import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.ScrolledComposite;

public class MainFrame {

	protected Shell shell;
	private Text search_tb;
	
	private String key = "AIzaSyA0LIgPvgyHpT2VpnAUHp-tY6hse27xHkU";
	private String cx = "012412805413164026341:bf7t47l-aea";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainFrame window = new MainFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public List<String> getSearchLinks(String query)
	{
		List<String> links = new LinkedList<String>();
		URL url;
		try {
			url = new URL("https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + cx + "&q=" + query + "&alt=json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream()), "UTF-8"));

	        String output;
	        System.out.println(url);
	        //System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	        	System.out.println(output);
	            if(output.contains("\"link\": \"")){                
	                String link=output.substring(output.indexOf("\"link\": \"")+
	                       ("\"link\": \"").length(), output.indexOf("\","));
	                //System.out.println(link);
	                links.add(link);
	            }     
	        }
	        conn.disconnect();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return links;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(609, 439);
		shell.setText("Online Movies URL Helper");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 189, 573, 182);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Link lblLinks = new Link(scrolledComposite, SWT.NONE);
		lblLinks.setText("Try Link from below:");
		lblLinks.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblLinks.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		scrolledComposite.setContent(lblLinks);
		scrolledComposite.setMinSize(lblLinks.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		lblLinks.addSelectionListener(new SelectionAdapter(){
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	               System.out.println("You have selected: "+e.text);
	               
	               try {
					Desktop.getDesktop().browse(new URL(e.text).toURI());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	    });
		
		Label lblBySa3dy = new Label(shell, SWT.NONE);
		lblBySa3dy.setAlignment(SWT.CENTER);
		lblBySa3dy.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblBySa3dy.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblBySa3dy.setBounds(316, 47, 98, 28);
		lblBySa3dy.setText("By Sa3dy");
		
		Label lblOnlineMoviesUrl = new Label(shell, SWT.NONE);
		lblOnlineMoviesUrl.setAlignment(SWT.CENTER);
		lblOnlineMoviesUrl.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.BOLD));
		lblOnlineMoviesUrl.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblOnlineMoviesUrl.setBounds(10, 10, 359, 38);
		lblOnlineMoviesUrl.setText("Online Movies URL Helper");
		
		Label lblVersion = new Label(shell, SWT.NONE);
		lblVersion.setAlignment(SWT.CENTER);
		lblVersion.setText("ver0.1");
		lblVersion.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblVersion.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblVersion.setBounds(521, 20, 62, 38);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmReportSomething = new MenuItem(menu_1, SWT.NONE);
		mntmReportSomething.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://www.facebook.com/Mostafa.Saady").toURI());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmReportSomething.setText("Report Something");
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmAboutAuthor = new MenuItem(menu, SWT.NONE);
		mntmAboutAuthor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://www.facebook.com/Mostafa.Saady").toURI());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mntmAboutAuthor.setText("About Author");
		
		search_tb = new Text(shell, SWT.BORDER);
		search_tb.setOrientation(SWT.RIGHT_TO_LEFT);
		search_tb.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		search_tb.setBounds(10, 81, 573, 28);
		
		Button btnSearch = new Button(shell, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Get Links pressed.");
				
				lblLinks.setText("Try Link from below:");
				
				if (!search_tb.getText().isEmpty()) {
					
					List<String> resultStrings = getSearchLinks(search_tb.getText());
					for (String resultString : resultStrings) {
						System.out.println(resultString);
						lblLinks.setText(lblLinks.getText() + "\n" + "<a>" + resultString + "</a>");
					}
				}
		        
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		btnSearch.setBounds(435, 115, 148, 38);
		btnSearch.setText("Get Links");
		
		Label lblLinksBox = new Label(shell, SWT.NONE);
		lblLinksBox.setText("Links Box:");
		lblLinksBox.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblLinksBox.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblLinksBox.setAlignment(SWT.CENTER);
		lblLinksBox.setBounds(10, 155, 98, 28);
		

	}
}
