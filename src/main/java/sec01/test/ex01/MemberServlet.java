package sec01.test.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MemberServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet 실행");
		
		String a_value = request.getParameter("a");
		System.out.println("a_value : "+ a_value);


		// db 활용
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			// db connection pool을 이용
			DataSource dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
			// db 접속
			Connection con = dataFactory.getConnection();
			
			
			String sql = "select * from emp";
			// sql 준비
			PreparedStatement ps = con.prepareStatement(sql);
			// sql 실행
			ResultSet rs = ps.executeQuery();
//			boolean hasNext = rs.next();
//			while( hasNext ){
//				String ename = rs.getString("ename");
//				int empno = rs.getInt("empno");
//				
//				hasNext = rs.next();
//			}

			response.setContentType("text/html; charset=utf-8;");
			PrintWriter out = response.getWriter();
			
			while( rs.next() ){
				String ename = rs.getString("ename");
				int empno = rs.getInt("empno");

				out.println("<div>");
				out.println("ename : "+ ename);
				out.println("empno : "+ empno);
				out.println("</div>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
