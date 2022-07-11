package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAnno(){
		String sql = "SELECT distinct YEAR(e.reported_date) as anno "
				+ "FROM events e  Order by anno" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> anno = new ArrayList<>() ;
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					anno.add(res.getInt("anno")); 	
				} 
				
				
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return anno;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<Integer> listMese(){
		String sql = "SELECT distinct MONTH(e.reported_date) as mese "
				+ "FROM events e Order by mese" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> mese = new ArrayList<>() ;
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					mese.add(res.getInt("mese")); 
						
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
		conn.close();
		return mese;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Integer> listGiorno(){
		String sql = "SELECT distinct DAY(e.reported_date) as giorno "
				+ "FROM events e Order by giorno " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> giorno = new ArrayList<>() ;
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					giorno.add(res.getInt("giorno")); 
					
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return giorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Adiacenza> getVertici(int a){
		String sql = "SELECT DISTINCT e1.district_id, e1.geo_lat AS e1Lat, e1.geo_lon AS e1Lon "
				+ "FROM events e1 "
				+ "where  Year(e1.reported_date)=? "
				+ "GROUP BY e1.district_id "
				+ "order by e1.district_id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Adiacenza> list = new ArrayList<>();
			st.setInt(1,a); 
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Adiacenza(res.getInt("district_id"),
							new LatLng(res.getDouble("e1Lat"),(res.getDouble("e1Lon")))));
							
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getDistretti(){
		String sql = "SELECT DISTINCT e1.district_id "
				+ "FROM events e1 ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>();
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("district_id"));
							
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private Object LatLng(double double1) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public List<Event> listAllEventsbyDate(Integer anno, Integer mese, Integer giorno){
		String sql = "	SELECT * "
				+ "	FROM events "
				+ "	WHERE YEAR(reported_date)=? "
				+ "and MONTH(reported_date)=?  "
				+ "and DAY(reported_date)=? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,  anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			List<Event> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Integer getDistrettoMin(Integer anno) {
		String sql="	Select district_id "
				+ "		From events "
				+ "		WHERE YEAR(reported_date)=? "
				+ "		GROUP BY district_id "
				+ "		ORDER BY COUNT(*) "
				+ "		LIMIT 1";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getInt("district_id");
			} else {
				conn.close();
				return null;
			}
			
			
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	
}
}