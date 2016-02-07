/*
 * Created by SharpDevelop.
 * User: Sa3dyLAP
 * Date: 2/7/2016
 * Time: 12:40 AM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using System.Net;
using Newtonsoft.Json.Linq;
using System.Diagnostics;

namespace OnlineMoviesURLHelperver0._
{
	/// <summary>
	/// Description of MainForm.
	/// </summary>
	public partial class MainForm : Form
	{
		public string apiKey = "AIzaSyCiSwIsMNNmlL_R8vkXHSxLOa9G4qcDae0";
		public string cx = "012412805413164026341:bf7t47l-aea";
		public string query = "hi";
		
		public MainForm()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
			
			//
			// TODO: Add constructor code after the InitializeComponent() call.
			//
		}
		
		AboutMe am_frm;
		
		void AboutAuthorToolStripMenuItemClick(object sender, EventArgs e)
		{
			System.Windows.Forms.Form f1 = System.Windows.Forms.Application.OpenForms["AboutMe"];
			if(((AboutMe)f1)!=null)
			{
				am_frm.Focus();
			}
			else
			{
				am_frm = new AboutMe();
				am_frm.Show();
			}
		}
		
		void Search_textBoxKeyPress(object sender, KeyPressEventArgs e)
		{
			if (e.KeyChar == (char)Keys.Enter)
	        {
	            e.Handled = true;
	            search_btn.PerformClick();
	        }
		}
		
		
		
		void Search_btnClick(object sender, EventArgs e)
		{
			if(search_textBox.Text != "")
			{
				LoadingForm load_frm = new LoadingForm();
				
				load_frm.StartPosition = FormStartPosition.CenterScreen;
				load_frm.Show();
				load_frm.Update();

				WebClient webClient = new WebClient();
				
				Debug.WriteLine(String.Format("https://www.googleapis.com/customsearch/v1?key={0}&cx={1}&q={2}&alt=json", apiKey, cx, search_textBox.Text));
				load_frm.Update();
				string result = webClient.DownloadString(String.Format("https://www.googleapis.com/customsearch/v1?key={0}&cx={1}&q={2}&alt=json", apiKey, cx, search_textBox.Text));
				load_frm.Update();
			    var jsonData = JObject.Parse(result);
			    load_frm.Update();
			    //logger_richTextBox.AppendText(jsonData["items"].ToString());
			    load_frm.Update();
			    logger_richTextBox.Clear();
			    load_frm.Update();
			    int link_counter = 1;
			    foreach(JObject obj in (JArray)jsonData["items"])
			    {
			    	load_frm.Update();
			    	logger_richTextBox.AppendText("لينك" + link_counter + ": " + obj["link"].ToString() + Environment.NewLine);
			    	link_counter++;
			    	load_frm.Update();
			    }
			    load_frm.Update();
			    load_frm.Close();
			}
			
		}
		
		void ReportSomethingToolStripMenuItemClick(object sender, EventArgs e)
		{
			System.Diagnostics.Process.Start("https://www.facebook.com/Mostafa.Saady");
		}
		
		void ExitToolStripMenuItemClick(object sender, EventArgs e)
		{
			System.Environment.Exit(0);
		}
		
		void Logger_richTextBoxLinkClicked(object sender, LinkClickedEventArgs e)
		{
			System.Diagnostics.Process.Start(e.LinkText);
		}
	}
}
