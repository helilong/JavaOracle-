  


insert into userinfo1 values(4,'��ʽ');

insert into userinfo1 values(5,'��ʽ5');


insert into userinfo1 values(6,'��ʽ6');

--  �洢���� ��ѯ����

create or replace procedure pro_select(lst out sys_refcursor)
as
begin
       open lst for select * from userinfo1;
       
       
  end;


--�����洢���̵��������
create or replace procedure pro_insert(id number,name varchar2)
as
begin
       insert into userinfo1 values(id,name);
       
       commit;
  end;
  
  call pro_insert(30,'����');
  
  select * from userinfo1;
  
  
  --�����洢���̵�������� ���� ���
  
create or replace procedure pro_insert2(id number,name varchar2,msg out number)
as
begin
       insert into userinfo1 values(id,name);
       commit;
       msg := 1;
  end;
 
    
    
    call pro_insert2(21,'Ԭ��');
    
    
    
    --oracle  �еķ�ҳ    �ֶ�  ���ǳ�֮Ϊ��  rovnum
    
    
    select id,uname from userinfo1;
    
    
    --��ѯidС��5
    select id,uname from userinfo1 where id<5;
    
    --��ѯid����2 С�� 5
    
      select rowid,rownum,id,uname from userinfo1 where rownum>2 and rownum<5;
    
    /*
    rownum�и��ص�
    rownum ֻʹ����С�ڻ�С�ڵ��ڣ�������е����ж� ��ôֻ�ܵ���1
    rownum ��oracle ϵͳ˳�������к�  ���صĵ�һ�з������ 1 �ڶ��� ��2 һ������
    rownum ���Ǵ�1 ��ʼ
    
    */
    -- > �� �������ò��˵�  ���� ��ѯ  �����ṹ������
  select rownum,id,uname from (select rownum rn,u.* from userinfo1 u where rownum<5) un  where un.rn>2;
 

select * from userinfo1; 
  
  --���Ҫ���з�ҳ   ÿҳ3��   �ڶ�ҳ  456 ��ʼ��4=��ҳ��-1��* ÿҳ����+1   ������6= ҳ��* ÿҳ�ĳ���
    select rownum,id,uname from (select rownum rn,u.* from userinfo1 u where rownum<=6) un  where un.rn>=4;
  
  --oracle by
  --oracle �еķ�ҳ���
  select * from (select rownum rn,�����.* from (select * from ���� where �������ʽ order by �ֶ� asc/desc) �����) 
  where rn>(��ǰҳ-1)*��ʾ���� and r<=��ǰҳ*��ʾ����
  
  
  --
  select * from (select rownum r,u.* from(select * from userinfo1 where uname like '��%' order by id asc) u) where r>(1-1*3) and r<=2*3;
  
  
  
  
  
create or replace procedure pro_fenye(tableName varchar2,currentPage number,pageSize number,keyName varchar2,keyValue varchar2,orderName varchar2,orderWay varchar2,sumCount out number,lst out sys_refcursor)
as
       v_sql varchar2(200);
begin
  --��ҳ��ѯ
       v_sql := 'select * from (select rownum r,u.* from(select * from '|| tableName ||' where '|| keyName ||' like '''|| keyValue ||''' order by '|| orderName ||' '|| orderWay ||') u) where r>'||(currentPage-1)*pageSize||' and r<='|| currentPage*pageSize'';

     open lst for v_sql;
     
     --ͳ��������
     
    v_sql := 'select cpunt(1) form '||tableName||'  where '|| keyName||' like '''||keyValue||'''';
     
     execute immediate v_sql into sumcount;
  end;
 
  
--�����洢����   ������  ��ҳ�Ĳ�ѯ ��ͳ�������� 
create or replace procedure pro_fenye1
(
       tableName varchar2,
       currentPage number,
       pageSize number,
       sumCount out number,
       lst out sys_refcursor
)

as
 v_sql varchar2(500);
 begin
   --��ҳ��ѯ
   
  v_sql:='select id,name from (select rownum rn,u.* from '||tableName||' u where rownum<='||currentPage||'*'||pageSize||') n where n.rn>('||currentPage||'-1)*'||pageSize||'';
   
   open lst for v_sql;
   
   --ͳ��������
   
   v_sql:='select count(1) from '||tableName;
   
   execute immediate v_sql into sumCount;
end;


  
 
  drop procedure   pro_fenye;
  
  
