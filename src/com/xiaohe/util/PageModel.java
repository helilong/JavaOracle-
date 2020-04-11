package com.xiaohe.util;

import java.util.List;

import com.xiaohe.model.Userinfo;



public class PageModel<T> {
	private int currentPage;//��ǰ�ڼ�ҳ
	private int sumPageCount;//�ܵ�ҳ��
	private int sumCount;//�ܵļ�¼����
	private int upPage;//��һҳ
	private int downPage;//��һҳ
	private int displayCount;//ÿһҳ��ʾ������
	private int firstPage=1;//��ҳ
	private int lastPage;//βҳ
	private List<Userinfo> list;//����
	/**
	 * �ڹ��췽���еõ�ÿһҳ��ʾ��������������������û��Լ���������
	 * 
	 * ͬʱ ����ҳ����Ĭ�ϲ��� 1  �� set �����Ϳ���ʡ����
	 * 
	 * �������ܼ�¼�����У�����ͬʱ�����ܵ�ҳ��
	 * @param displayCount
	 */
	
	public PageModel(int displayCount){
		
		this.displayCount=displayCount;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	//�����õ�ǰҳ��ʱ�����ͬʱ��������ҳ
	public void setCurrentPage(int currentPage){
		this.currentPage=currentPage;
		
	}
	public int getSumPageCount(){
		return sumPageCount;
	}
	public int getSumCount(){
		
		return sumCount;
	}
	//�������ܵļ�¼������ʱ�����ͬʱ����������һЩ����
	public void setSumCount(int sumCount){
		this.sumCount=sumCount;
		//�õ��ܵļ�¼����ʱ��Ϳ��Եõ��ܵ�ҳ��
		this.sumPageCount=(int)Math.ceil(sumCount/(float)displayCount);
		//���ܵ�ҳ������������һҳ��������
		this.lastPage=this.sumPageCount;
	}
	//��ȡ��һҳ����ǰС�ڻ������ҳ��ʱ�򣬷�����ҳ����������ҳ��ʱ�򣬵�ǰҳ-1
	public int getUpPage(){
		if(this.currentPage<=this.firstPage){
			return this.firstPage;
			
		}
		return this.currentPage-1;
	}
	public void setUpPage(int upPage){
		this.upPage=upPage;
	}
	//��ȡ��һҳ����ǰҳ���ڻ��ߵ������һҳ��ʱ�򷵻����һҳ����С�����һҳ��ʱ��ǰҳ+1
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
