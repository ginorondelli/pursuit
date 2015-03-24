package org.vaadin.domain;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class PursuitMeta {

	public static LinkedHashSet<String>allAgents() {
		String[] agents={"John Webb", "Paul W", "Roger", "John"};
		return new LinkedHashSet<String>(Arrays.asList(agents));
	}
	
	
	public static LinkedHashSet<String> allSourceSectors() {
		String[]sectorsArr={"Manufacturing",
							"--Discrete", 
							"--Process", 
							"----Chemicals", 
							"----Food and Beverage",
							"--Apparel",
							"--Engineering",
							"Professional Services",
							"--Finance",
							"--Legal",
							"--Recruitment",
							"--Consultancy",
							"--Marketing",
							"Charities/NFP",
							"Wholesale/Distribution",
							"--Logistics",
							"--Transport",
							"--Warehousing",
							"Public Sector",
							"Education",
							"--Colleges/Universities",
							"--Schools",
							"----State",
							"----Private",
							"Retail & Leisure",
							"Construction"
							
		};
		LinkedHashSet<String>sectors=new LinkedHashSet<String>(Arrays.asList(sectorsArr)); 
		//Set sectors
		return sectors;
	}
	public static LinkedHashSet<String>allPartnerTypes() {
		String[]partnerTypes={"Microsoft", "SAP", "Sage", "Epicor", "Infor", "Access", "Other"};
		return new LinkedHashSet<String>(Arrays.asList(partnerTypes));
	}
	
	
	public static LinkedHashSet<String>allTurnovers() {
		String[]turnovers={"A: 0 to 1M","B: 1M to 5M","C: 5M to 10M","D: 10M to 20M","E: 20M to 50M","F: 50M to 100M","G: 100M to 250M","H: 250M to 500M","I: 500M to 1 Billion","J: > 1 Billion"};
		return new LinkedHashSet<String>(Arrays.asList(turnovers));
	}
	public static LinkedHashSet<String>allEmployeeBanding() {
		String[]empBandings={"A: 1-9","B: 10-19","C: 20-49","D: 50-99","E: 100-199","F: 200-499","G: 500-999","H: 1000-4999","I: 5000 Plus"};
		return new LinkedHashSet<String>(Arrays.asList(empBandings));
	}
	
	public static LinkedHashSet<String>allRegions() {
		String[]empBandings={"Channel Islands","East Midlands","Eastern","Greater London","Home Counties","North East","North West","Northern Ireland","Rep. Ireland","Scotland (North)","Scotland (South & Central)","South East","South West","Wales","West Midlands","Yorkshire & Humberside"};
		return new LinkedHashSet<String>(Arrays.asList(empBandings));
	}	
	public static LinkedHashSet<String>allPostCodes() {
		String[]postCodes={"AB","AL","B","BA","BB","BD","BH","BL","BN","BR","BS","BT","CA","CB","CF","CH","CM","CO","CR","CT","CV","CW","DA","DD","DE","DG","DH","DL","DN","DT","DY","E","EC","EH","EN","EX","FK","FY","G","GL","GU","HA","HD","HG","HP","HR","HS","HU","HX","IG","IP","IV","KA","KT","KW","KY","L","LA","LD","LE","LL","LN","LS","LU","M","ME","MK","ML","N","NE","NG","NN","NP","NR","NW","OL","OX","PA","PE","PH","PL","PO","PR","RG","RH","RM","S","SA","SE","SG","SK","SL","SM","SN","SO","SP","SR","SS","ST","SW","SY","TA","TD","TF","TN","TQ","TR","TS","TW","UB","W","WA","WC","WD","WF","WN","WR","WS","WV","YO","ZE","Y","JE","IM"};
		return new LinkedHashSet<String>(Arrays.asList(postCodes));
	}
	public static LinkedHashSet<String> allSolutions() {
		String[]solutionsArr={"ERP",
							"--ERP Implementation", 
							"--ERP Support/Upgrade", 
							"----Dynamics Support", 
							"----Dynamics Upgrade",
							"----AX Support",
							"----AX Upgrade",
							"----ERP Construction",
							"----Sage 200 support",
							"----Sage 200 Upgarde",
							"----SAP B1 Support",
							"----SAP Support ",
							"----SAP Upgrade",
							"Best of Breed",
							"--MRP", 
							"--WMS", 
							"--Asset Management", 
							"--Time Management", 
							"--EPOS", 
							"--Expense Management", 
							"--Facilities Management", 
							"--HR Software", 
							"--Project Accounting", 
							"--Job costing", 
							"--Quality Management", 
							"--Stock Control",						
							"CRM",
							"--Bespoke crm",
							"--CRM Support",
							"----Dynamics CRM Support",
							"----Sugar CRM Support",
							"----Sage CRM Support",
							"--CRM upgrade",
							"--CRM Implementation",
							"BI",
							"--BI Support",
							"--BI Implementation",
							"Document Management",
							"--Invoice Automation",
							"--Document Management",
							"--Case Management",
							"--Sharepoint",
							"--file sharing",
							"Finance Only",
							"--NAV Support",
							"--Dynamics GP upgrade",
							"--NAV Upgrade",
							"--Pegasus Support"
							};
		LinkedHashSet<String>solutions=new LinkedHashSet<String>(Arrays.asList(solutionsArr)); 
		return solutions;
	}
	public static LinkedHashSet<String> allConsultancy() {
		String[]consultancyArr={"Bespoke Development",
							"--E-Commerce", 
							"--Application Development", 
							"--EDI", 
							"--SQL Server upgrade",
							"Testing",
							"--Penetration Testing"
		};
		LinkedHashSet<String>consultancy=new LinkedHashSet<String>(Arrays.asList(consultancyArr)); 
		return consultancy;
	}
		public static LinkedHashSet<String> allManagedServices() {
			String[]managedServicesArr={
								"Virtualisation",
								"--VDI", 
								"--Azure ", 
								"--Replication", 
								"--Hosted Desktop",
								"--Hyper-V remote Management",
								"Cloud",
								"--Office 365", 
								"--Cloud Back-up ", 
								"--Cloud Computing", 
								"--Cloud Hosting",
								"--Cloud Services",
								"--Cloud Solution ",
								"--Cloud Storage",
								"--Lync",
								"Disaster Recovery",
								"--Cloud Backup", 
								"--On-site", 
								"--Project Implementation", 
								"Infrastructure",
								"--Cabling", 
								"--Data Warehousing", 
								"--Server Upgrades",
								"----SBS 2003 Upgrade",
								"----Exchange Upgrade",
								"--Desktop Upgrades", 
								"--Active Directory", 
								"--Windows Upgrade",
								"--Hardware Support", 
								"--Network Management", 
								"--Server migration",
								"IT Support",
								"--1st Line Support", 
								"--2nd Line Support", 
								"--3rd Line Support", 
								"Storage",
								"--Full Support", 
								"--Email Archiving"
			};
			LinkedHashSet<String>managedServices=new LinkedHashSet<String>(Arrays.asList(managedServicesArr)); 
			return managedServices;
		}
		public static LinkedHashSet<String> allTelecoms() {
			String[]telecomsArr={"Phone Systems",
								"--Phone System Implementation", 
								"--Phone System Support", 
								"Data", 
								"--Leased Lines",
								"--Broadband",
								"Lines and Calls",
								"--Landlines",
								"--Mobiles",
								"--SIP",
								"--Unified Comms"
			};
			LinkedHashSet<String>telecoms=new LinkedHashSet<String>(Arrays.asList(telecomsArr)); 
			return telecoms;
		}
	
}
