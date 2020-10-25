package com.book.tx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Download {

	public static void main(String[] args) {

		SatrtDown();
	}

	private static String bookname;
	private static String bookurl;
	private static String download;

	static {
		Properties p = new Properties();// 获取配置文件
		try {
//			new File("./");
//			File directory = new File("");//参数为空
//			String courseFile = directory.getCanonicalPath() ;
//			String author =directory.getAbsolutePath();
//			System.out.println(courseFile  +"   |   "+author);
			p.load(new FileReader(new File("book.config")));
			bookname = p.getProperty("bookname");
			bookurl = p.getProperty("bookurl");
			download = p.getProperty("download");
			System.out.println("配置文件加载完成!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 通过章节的链接获取章节对象
	 */
	private static BokeName getelement(String url) {
		BokeName bk = new BokeName();
		try {
			// 通过 连接地址 获取 小说内容和 目录 "http://www.biquge.com.tw/14_14055/9194140.html"
			// jsoup发送一个get请求获取网页内容
			Document content = Jsoup.connect(url).get();
			// 获取章节内容的文本信息
			String getdoc = content.getElementById("content").text();
			// 获取章节的名称
			String name = content.getElementsByTag("h1").text();
			// System.out.println(name + " " + getdoc);
			System.out.println("章节加载完毕！");
			bk.setContent(getdoc);
			bk.setName(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bk;
	}

	/**
	 * 根据小说首页的链接获取所有小说章节的链接
	 * 
	 * @param url
	 * @return http://www.biquge.com.tw/14_14055/
	 */
	private static List<String> getallurl(String url) {

		List<String> list = new ArrayList<>();
		try {
			//根据地址获取页面上的所有内容
			Document content = Jsoup.connect(url).get();
			//根据 标签获取 页面上 需要的 内容
			Elements allurl = content.getElementById("list").getElementsByTag("a");
			//便利内容 存入list
			for (Element aurl : allurl) {
				// 获取所有章节链接的绝对路径
				String link = aurl.attr("abs:href");
				list.add(link);
				System.out.println(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 启动下载 下载小说
	 */

	private static Book SatrtDown() {

		Book bk = new Book();
		bk.setUrl(bookurl);
		bk.setNames(bookname);

		BufferedWriter bf = null;
		List<BokeName> charpters = new ArrayList<>();
		try {
			File file = new File(download + bookname + ".txt");
			if(!file.exists())
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter out = new FileWriter(file);
			bf = new BufferedWriter(out);
			//// 获取了所有的章节链接
			List<String> ll = getallurl(bookurl);
			for (String s : ll) {
				BokeName charpter = getelement(s);
				bf.write(charpter.getName());
				bf.newLine();
				bf.newLine();
				bf.write(charpter.getContent());
				bf.newLine();
				bf.newLine();
				System.out.println("章节:  " + charpter.getName() + "   已下载!");
				charpters.add(charpter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bk;
	}
}
