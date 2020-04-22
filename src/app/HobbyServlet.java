package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/hobby")
public class HobbyServlet extends HttpServlet {

	/********************************************************************************
	 * 以下のdoGet/doPostを実装して下さい。
	 * importなどは自由に行って下さい。
	 * @throws IOException
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		response.setContentType("text/html; charset=Windows-31J");
		// TODO 必須機能「趣味参照機能」
		// アクセス元のHTMLでitemCdに設定された値を取得して、String型の変数itemCdに代入
		String shainId = request.getParameter("shainId");
		System.out.println("shainId=" + shainId);

		// JDBCドライバの準備
		try {

			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "wt2";
		String pass = "wt2";

		// 実行するSQL文
		String sql ="select  \n" +
				"MH.HOBBY_NAME mhn, \n" +
				"MC.CATEGORY_NAME  mcn \n" +
				"from  \n" +
				"MS_HOBBY MH \n" +
				",MS_CATEGORY MC \n" +
				",MS_SYAIN MS \n" +
				",MS_SYAIN_HOBBY MSH \n" +
				"where 1=1 \n" +
				"and MS.SYAINID = MSH.SYAINID \n" +
				"and MSH.HOBBY_ID = MH.HOBBY_ID \n" +
				"and MH.CATEGORY_ID = MC.CATEGORY_ID \n" +
				" \n" +
				"and MS.SYAINID = '0001' \n" +
				" \n" +
				"order by \n" +
				"MH.HOBBY_ID " ;
		
		
		List <Hobby> hobbyList =  new ArrayList<>();

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();

				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行後の処理内容

			// SQL実行結果を保持している変数rsから商品情報を取得
			// rs.nextは取得した商品情報表に次の行があるとき、trueになります
			// 次の行がないときはfalseになります
			while (rs1.next()) {
				Hobby h1 = new Hobby();
				h1.setHobby(rs1.getString("mhn")); // Item型の変数itemに商品コードをセット
				h1.setHobbyCategory(rs1.getString("mcn"));// Item型の変数itemに商品名をセット
				hobbyList.add(h1);
				System.out.println(h1.getHobby());
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString(hobbyList));
		// -- ここまで --
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO 任意機能「趣味投稿機能に挑戦する場合はこちらを利用して下さい」

		// -- ここまで --
	}

}
