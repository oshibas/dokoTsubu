package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DeleteMutterListLogic;
import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
	//つぶやきリストをアプリケーションスコープから取得
	GetMutterListLogic getMutterListLogic =
		new GetMutterListLogic();
	List<Mutter> mutterList = getMutterListLogic.execute();
	request.setAttribute("mutterList", mutterList);
		
	//ログインしてるか確認
	//セッションスコープからユーザー情報を取得
	HttpSession session = request.getSession();
	User loginUser = (User)session.getAttribute("loginUser");
	
	if(loginUser == null) {
		//redirect
		response.sendRedirect("index.jsp");
	}else{
		//forward
		RequestDispatcher dispatcher =
			request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request,  response);
		}
	}
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException{
	//リクエストパラメーターの取得
	request.setCharacterEncoding("UTF-8");
	String text = request.getParameter("text");
	
	//★CRUD 削除用追記
    String muIdStr = request.getParameter("muId");
    int muId = 0; // <- 変数の初期化

    if (muIdStr != null && !muIdStr.isEmpty()) {
        muId = Integer.parseInt(muIdStr);
    }
    String name = request.getParameter("name");
	
	//入力値チェック
    if (text != null && !text.isEmpty()) {
		//セッションスコープに保存されたユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		//つぶやきをつぶやきリストに追加
		Mutter mutter = new Mutter(loginUser.getName(), text);
		PostMutterLogic postMutterLogic = new PostMutterLogic();
		postMutterLogic.execute(mutter);
		
		//★CRUD 削除用追記
    } else if (muId != 0) {
		
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
	    DeleteMutterListLogic deleteMutterLogic = new DeleteMutterListLogic();
	    deleteMutterLogic.execute(loginUser.getName(), muId);
	    
	    if (!loginUser.getName().equals(name)) {
	        request.setAttribute("errorMsg", "自分のつぶやきのみ削除できます");
	    }
		
	}else {
		//エラーメッセージをリクエストスコープに保存
		request.setAttribute("errorMsg",
			"つぶやきが入力されていません");
	}
	
	//つぶやきリストを取得して、リクエストスコープに保存
	GetMutterListLogic getMutterListLogic =
		new GetMutterListLogic();
	List<Mutter> mutterList = getMutterListLogic.execute();
	request.setAttribute("mutterList", mutterList);
	
	//メイン画面にフォワード
	RequestDispatcher dispatcher = request.getRequestDispatcher(
		"WEB-INF/jsp/main.jsp");
	dispatcher.forward(request, response);
	}
}
