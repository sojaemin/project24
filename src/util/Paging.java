package util;

public class Paging
{
    public int getPageCount(int numPerPage, int dataCount) // 전체 페이지 수 구하기
    {
        // numPerPage 는 한페이지 보여주려는 게시물 수. action에서 그 수를 지정.
        // dataCount 는 등록된 총 게시물 수. action에서 그 수를 sql로 값을 가져옴.
        
        int pageCount = 0; // 페이징 수. 1페이지, 2페이지 관련 페이지수.
                
        // 포인트는 페이지 수가 나누어 떨어지지 않을때 +1을 해줘야함.
        if(dataCount % numPerPage == 0)
        {
            pageCount = dataCount / numPerPage; // 페이지 수를 구하게 됨.
        }
        else if(dataCount % numPerPage != 0)
        {
            pageCount = (dataCount / numPerPage) + 1; // 나누어 떨어지지 않을 경우 남은 게시물은 새 페이지.
        }
        
        return pageCount; // action에서 total_page 로 쓰임.(전체페이지 수)
    }
    /* ex) 111개의 개시물이 등록 되어있다고 했을때(dataCount)
     * 한 페이지에 보여주려는 게시물 수 (10개)로 나눈다.(numPerPage)
     * 이경우 11개의 페이지가 나오게되고(pageCount)
     * 남은 1개(dataCount % numPerPage)는
     * 12페이지(pageCount = (dataCount / numPerPage) + 1;)에 보여지게 됨.
     */
    
    public String pageIndexList(int current_page, int total_page, String list_url) // 페이지 처리(Java(action)를 이용해 url을 설정할때.??)
    {
        // current_page 는 현재 보여줄(표시할) 페이지. action에서 값을 받아옴.
        // total_page 는 전체 페이지 수. action에서 값을 받아오나, 위에있는 매서드 pageCount 리턴값임.
        // list_url 는 링크 주소.
        
        int numPerBlock = 10; // view페이지 하단에 보여 지는 페이지 버튼 개수. ex) 1 2 3 4 5 6 ..[next]
        
        if(current_page == 0) // 현재 페이지의 개수가 없으면.
        {
            /* 여기는 action에서 total_page 수(위에있는 getPageCount 매서드에서 리턴된 수)가
             * current_page 수 보다 작으면 return null로 현재 매서드가 중단된다.
             * 이 말은 현재 페이지 외에 보여줄게 없다라는 말로, action에서 current_page는 초기값 1,
             * total_page는 게시물 수에따라 다르지만 초기값은 0 이므로,
             * 한페이지가 넘지 않을경우 페이징 처리는 필요없기때문에 매서드 중단이 된다.
             */
            return null;
        }
        
        if(list_url.indexOf("?") != -1) // 넘어오는 주소에 ?(구분자) 가 있을 경우
        {
            list_url = list_url + "&";
            // 이말은 예로 주소가 /list.do?xxx로 주소가 이미 넘어 올 경우 뒤에 &를 붙여 구분을 함.
            // 이뜻은 action에서 검색(내용, 제목 등)을 하게되면 구분자로 구분을 두어 검색 주소를 표시하기 위함인듯함.
            // 예로 list.do?searchKey=자바&searcValue=쓰레드
            // 라고 주소가 오게되면 & 를 붙여 구분한다는 말임.
            // list.do?searchKey=자바&searcValue=쓰레드&pageNum=2 요렇게..
        }
        else
        {
            list_url = list_url + "?";
            // /list.do로 주소가 넘어오게 되면 뒤에 ?를 붙이게 됨.
            // 만약 2페이지를 보게될경우 주소는 list.do?pageNum=2 요렇게 설정되는거임.
        }
        
        int currentPageSetUp = (current_page / numPerBlock) * numPerBlock; // 표시할 첫 페이지
        // currentPageSetUp은 0 아니면 10 아니면 20 아니면 30...설정됨.
        
        if(current_page % numPerBlock == 0)
        {
            currentPageSetUp = currentPageSetUp - numPerBlock;
            /* 현재 페이지를 numPerBlock(하단 페이지 수)으로 나눈 나머지가 0이면 위에서 구한 currentPageSetUp에서 numPerBlock을 뺀다.
             * 이말은 만약, 현재 페이지가 32 페이지를 보고있을 경우 currentPageSetUp의 값은 30이다.
             * 32를 0으로 나누면 나머지가 있으므로..
             * currentPageSetUp는 30 그대로 되게된다.
             * 
             * 만약 40페이를 보게되면 40은 나누면 나머지가 0이므로 currentPageSetUp값은 30이 되고,
             * 밑에 보여지게 되는 페이지는
             * [Prev]30, 31, 32, 33, 34, 35, 36, 37, 38, 39[Next] 처럼 보여지게 하기위한
             * 밑작업이다..
             * 
             * 중요한건! 밑에서 int page = currentPageSetUp + 1;를 구하게 되는데
             * 실질 적으로 하단에 보여지는 페이지 값은
             * [Prev]31, 32, 33, 34, 35, 36, 37, 38, 39, 40[Next]
             * 이 되게 되는 것이다.
             */
        }
        
        StringBuffer strList = new StringBuffer(); // 실질적으로 JSP에서 기능을 담당하는 변수다.
        // JSP에서 페이지 번호를 누르게되면 이놈을 누르는거랑 같은거.
        
        // 1페이지
        if((total_page > numPerBlock) && (currentPageSetUp > 0))
        {
            /* total_page가 numPerBlock보다 크고(즉, 하단에 보여지는 페이지 수가 10개고, 계산되어진 총 페이지 수가 11 이상이라면..)
             * currentPageSetUp가 0 이상 이라면(즉, 현재 페이지가 11페이지 이상이란 얘기)
             */
            strList.append("<a href='" + list_url + "pageNum=1'>1</a> ");
            // 이말은 1...10 11 12 13... 이말임.
        }
        
        int n = current_page - numPerBlock; // 현재 페이지에서 10을(하단에 보여지는 페이지 개수) 뺌.
        
        if((total_page > numPerBlock) && (currentPageSetUp > 0))
        {
            strList.append("[<a href='" + list_url + "pageNum=" + n + "'>Prev</a>] ");
            /* 위랑 같은 내용인데 하단에 보여지는 페이지 보다 크고, 현제 페이지가 11 이상이면
             * 1 [Prev] 11..가 보여지게 되는곳. 여기서 n은 [Prev]를 눌렀을때 가는 페이지로,
             * n은 위에서 구했듯이 만약 현제 페이지가 32라면 22값이되어 n 페이지는 22페이지가 된다.
             * 즉, 32페이지에서[Prev]를 누르면 22페이지로 이동하게 되는 것이다.
             */
        }
        
        int page = currentPageSetUp + 1; // 0부터 시작이 아닌 1부터 시작하기 위한 덧셈.
        
        // 바로 가기 페이지 구현(즉, [Prev] .... [Next] 안에 .... 구현.
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock))
        {
            /* page가 전페 페이지랑 같거나 작고, 페이지가 하단에 보여지는 페이지 개수에 10을 더한값보다 작거나 같을때 까지 무한 반복.
             * 이말은 1 ~ 11 페이지가 있을때, 토탈 페이지는 11까지 있고
             * 하단에 보여지는 페이지는 10개일 경우 20보다 작거나 같을때 까지 반복이다.
             * 정리 하면, 1부터 11까지 반복이고, 1부터 20까지 반복인 것이다.
             * 두 조건중에 하나라도 맞으면 while문을 벗어나게 된다.
             */
            
            if(page == current_page)
            {
                strList.append("<font color = 'Fuchsia'>" + page + " </font>");
                // 현재 페이지랑 설정해둔 페이지 즉, 하단에 보여지는 페이지랑 같으면 링크 없게 만드는것.
            }
            else
            {
                strList.append("<a href = '" + list_url + "pageNum=" + page + "'>" + page + "</a> ");
                // 그 외에는 전부 링크를 걸어 페이지가 변경됨.
            }
            
            page++; // 페이지를 계속 더해 최종 11페이지까지 진행하게 만들기 위함.
        }
        
        n = current_page + numPerBlock;
        
        // [Next] 나타내기.
        if((total_page - currentPageSetUp) > numPerBlock)
        {
            /* 하단에 보여지는 페이지 개수(10개)가 전체 페이지에서 currentPageSetUp 개수를 뺐을때 보다 크면.
             * 이말은, 전체 페이지가 40페이지 이면, 40에서 30을 뺀다. 이 값이 10보다 크면 [Next]를 마지막에 출력하게 된다.
             * 만약 32페이지가 전체 페이지이고 현제 22페이지를 보고있다면,
             * 32에서 20을 빼게 된다.
             * 위에서 계산한 n은 [Next]의 링크인데
             * 현제 페이지가 22일 경우 22 + 10의 값 32가 되게된다.
             * 즉, [Next]의 링크는 32가 되게 되는 것이다.
             * 
             * 또한, 32페이지가 전체 페이지 일경우 현제 페이지가 31이라면
             * 전체 페이지 32에서 30을 빼개 되는데 이때는 하단에 보여지는 페이지 개수 10보다 작으므로
             * [Next]는 화면상에 출력되지 않는 것이다.
             */
            
            strList.append("[<a href='"+list_url+"pageNum="+n+"'>Next</a>] ");
        }
        
        // 마지막 페이지 구현
        if((total_page > numPerBlock) && (currentPageSetUp + numPerBlock) < total_page)
        {
            /* 전체페이지가 하단에 보여지는 개수 보다 크고, 전체페이지가 currentPageSetUp에 10을 더한 값보다 작을 때
             * 즉, 전체 페이지가 32 일 경우 하단에 보여지는 페이지 개수 10 보다 크고
             * 현제 22페이지를 볼경우 20 + 10은 전체페이지 32보다 작다.
             * 조건에 성립 하므로 하단에 보여지는 페이지 개수 제일 우측에([Next]다음에)
             * 전체페이지(total_page(32))가 화면에 표시되고 해당 링크로 이동 가능하게 된다.
             */
            strList.append("<a href='" + list_url + "pageNum=" + total_page + "'>" + total_page + "</a>");
        }
        
        return strList.toString();
        /* StringBuffer에 저장해둔(JSP에서 클릭하는) 문자열을 리턴한다.
         * 리턴하는 문자열은 pageIndexList이므로 ${pageIndexList}이라고 jsp에 적어두게 된다.
         */
    }
}