package com.xiaohe.util;

import java.util.List;

import com.xiaohe.model.Userinfo;



public class PageModel<T> {
	private int currentPage;//当前第几页
	private int sumPageCount;//总的页数
	private int sumCount;//总的记录条数
	private int upPage;//上一页
	private int downPage;//下一页
	private int displayCount;//每一页显示的条数
	private int firstPage=1;//首页
	private int lastPage;//尾页
	private List<Userinfo> list;//数据
	/**
	 * 在构造方法中得到每一页显示的条数，这个参数是由用户自己传过来的
	 * 
	 * 同时 给首页设置默认参数 1  其 set 方法就可以省略了
	 * 
	 * 在设置总记录条数中，可以同时设置总的页数
	 * @param displayCount
	 */
	
	public PageModel(int displayCount){
		
		this.displayCount=displayCount;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	//在设置当前页的时候可以同时设置上下页
	public void setCurrentPage(int currentPage){
		this.currentPage=currentPage;
		
	}
	public int getSumPageCount(){
		return sumPageCount;
	}
	public int getSumCount(){
		
		return sumCount;
	}
	//在设置总的记录条数的时候可以同时设置其他的一些参数
	public void setSumCount(int sumCount){
		this.sumCount=sumCount;
		//得到总的记录数的时候就可以得到总的页数
		this.sumPageCount=(int)Math.ceil(sumCount/(float)displayCount);
		//当总的页数算出来后，最后一页可以设置
		this.lastPage=this.sumPageCount;
	}
	//获取上一页，当前小于或等于首页的时候，返回首页，当大于首页的时候，当前页-1
	public int getUpPage(){
		if(this.currentPage<=this.firstPage){
			return this.firstPage;
			
		}
		return this.currentPage-1;
	}
	public void setUpPage(int upPage){
		this.upPage=upPage;
	}
	//获取下一页，当前页大于或者等于最后一页的时候返回最后一页，当小于最后一页的时候当前页+1
	public int getDownPage(){
		if(this.currentPage>this.lastPage){
			return this.lastPage;
		}
		return this.currentPage+1;
	}
	public void setDownPage(int downPage){
		this.downPage=downPage;
	}
	public int getDisplayCount(){
		return displayCount;
	}
	public void setDisplayCount(int displayCount){
		this.displayCount=displayCount;
	}
	public int getFirstPage(){
		return firstPage;
	}
	public void setFirstPage(int firstPage){
		this.firstPage=firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public List<Userinfo> getList() {
		return list;
	}

	public void setList(List<Userinfo> list) {
		this.list = list;
	}
	

}
