package util;

import java.awt.ItemSelectable;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * multipart/form-data
 */
public class FileUploadRequestWrapper extends HttpServletRequestWrapper {

    private boolean multipart = false;
    private Map<String, String[]> parameterMap;
    private Map<String, FileItem[]> fileItemMap;

    public FileUploadRequestWrapper(HttpServletRequest request)
            throws FileUploadException {
        this(request, -1, -1, null);
    }

    public FileUploadRequestWrapper(HttpServletRequest request, int threshold,
            int max, String repositoryPath) throws FileUploadException {
        super(request);
        parsing(request, threshold, max, repositoryPath);
    }

    private void parsing(HttpServletRequest request, int threshold, int max,
            String repositoryPath) throws FileUploadException {
        if (ServletFileUpload.isMultipartContent(request)) {
            multipart = true;
            parameterMap = new HashMap<String, String[]>();
            fileItemMap = new HashMap<String, FileItem[]>();
            DiskFileItemFactory factory = new DiskFileItemFactory();
           
            // 메모리에 저장할 사이즈, 기본사이즈 10kb
            if (threshold != -1) {
                factory.setSizeThreshold(threshold);
            }
            // 임시 저장 경로, 기본적으로 시스템의 탬프 , 웹서버의 경로
            if (repositoryPath != null) {
                factory.setRepository(new File(repositoryPath));
            }
           
            ServletFileUpload upload = new ServletFileUpload(factory);
            String encodingName = request.getCharacterEncoding();
            System.out.println(encodingName);
            if (encodingName == null)
                encodingName = "utf-8";
            upload.setSizeMax(max); // 최대 업로드 사이즈 (total)

            // upload.setFileSizeMax(); // 개별 업로드 사이즈
            List<FileItem> list = upload.parseRequest(request);
            for (int i = 0; i < list.size(); i++) {
                FileItem fileItem = list.get(i);
                String name = fileItem.getFieldName();
                if (fileItem.isFormField()) {
                    // 일반 필드
                    try {
                        String value = fileItem.getString(encodingName);
                        String[] values = parameterMap.get(name);
                        if (values == null) {
                            // 한글이 만약 깨진다면  아래처럼 변경, 예전 FileUpload API,
                            // values = new String[]{new String(value.getBytes("iso-8859-1"),encodingName)};
                            values = new String[] { value };
                        } else {
                            String[] tempValues = new String[values.length + 1];
                            System.arraycopy(values, 0, tempValues, 0, values.length);
                            // 한글이 깨진다면
                            // tempValues[tempValues.length - 1] = new String(value.getBytes("iso-8859-1") ,encodingName);
                            tempValues[tempValues.length - 1] = value;
                            values = tempValues;
                        }
                        parameterMap.put(name, values);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    // input type="file" 인 경우  fileItemMap에 저장
                    FileItem[] items = fileItemMap.get(name);
                    if (items == null) {
                        items = new FileItem[] { fileItem };
                    } else {
                        FileItem[] tempItems = new FileItem[items.length + 1];
                        System.arraycopy(items, 0, tempItems, 0, items.length);
                        tempItems[tempItems.length - 1] = fileItem;
                        items = tempItems;
                    }
                    fileItemMap.put(name, items);
                }
            }
        }
    }

    public boolean isMultipartContent() {
        return multipart;
    }

    public String getParameter(String name) {
        if (multipart) {
            String[] values = (String[]) parameterMap.get(name);
            if (values == null)
                return null;           
            return values[0];
        } else
            return super.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        if (multipart)
            return (String[]) parameterMap.get(name);
        else
            return super.getParameterValues(name);
    }

    public Enumeration getParameterNames() {
        if (multipart) {
            return new Enumeration() {
                Iterator iter = parameterMap.keySet().iterator();

                public boolean hasMoreElements() {
                    return iter.hasNext();
                }

                public Object nextElement() {
                    return iter.next();
                }
            };
        } else {
            return super.getParameterNames();
        }
    }

    public Map getParameterMap() {
        if (multipart)
            return parameterMap;
        else
            return super.getParameterMap();
    }

    public FileItem getFileItem(String name) {
        if (multipart) {
            FileItem[] fileItems = fileItemMap.get(name);
            if (fileItems != null) {
                return fileItems[0];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public FileItem[] getFileItemValues(String name) {
        if (multipart)
            return fileItemMap.get(name);
        else
            return null;
    }

    public void delete() {
        if (multipart) {
            Iterator fileItemIter = fileItemMap.values().iterator();
            while (fileItemIter.hasNext()) {
                FileItem[] fileItems = (FileItem[]) fileItemIter.next();
                for (FileItem item : fileItems) {
                    item.delete();
                }
            }
        }
    }
    
    public Map getFileItem() {
    	  if (multipart)
    	   return fileItemMap;
    	  else
    	   return null;
 	 } 

} 