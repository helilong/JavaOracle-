  


insert into userinfo1 values(4,'方式');

insert into userinfo1 values(5,'方式5');


insert into userinfo1 values(6,'方式6');

--  存储过程 查询数据

create or replace procedure pro_select(lst out sys_refcursor)
as
begin
       open lst for select * from userinfo1;
       
       
  end;


--创建存储过程的添加数据
create or replace procedure pro_insert(id number,name varchar2)
as
begin
       insert into userinfo1 values(id,name);
       
       commit;
  end;
  
  call pro_insert(30,'龙龙');
  
  select * from userinfo1;
  
  
  --创建存储过程的添加数据 输入 输出
  
create or replace procedure pro_insert2(id number,name varchar2,msg out number)
as
begin
       insert into userinfo1 values(id,name);
       commit;
       msg := 1;
  end;
 
    
    
    call pro_insert2(21,'袁华');
    
    
    
    --oracle  中的分页    字段  我们称之为列  rovnum
    
    
    select id,uname from userinfo1;
    
    
    --查询id小于5
    select id,uname from userinfo1 where id<5;
    
    --查询id大于2 小于 5
    
      select rowid,rownum,id,uname from userinfo1 where rownum>2 and rownum<5;
    
    /*
    rownum有个特点
    rownum 只使用于小于或小于等于，如果进行等于判断 那么只能等于1
    rownum 是oracle 系统顺序分配的行号  返回的第一行分配的是 1 第二行 是2 一次类推
    rownum 总是从1 开始
    
    */
    -- > 号 下载是用不了的  内容 查询  整个结构集出来
  select rownum,id,uname from (select rownum rn,u.* from userinfo1 u where rownum<5) un  where un.rn>2;
 

select * from userinfo1; 
  
  --如果要进行分页   每页3行   第二页  456 起始行4=（页码-1）* 每页长度+1   结束行6= 页码* 每页的长度
    select rownum,id,uname from (select rownum rn,u.* from userinfo1 u where rownum<=6) un  where un.rn>=4;
  
  --oracle by
  --oracle 中的分页语句
  select * from (select rownum rn,表别名.* from (select * from 表名 where 条件表达式 order by 字段 asc/desc) 表别名) 
  where rn>(当前页-1)*显示条数 and r<=当前页*显示条数
  
  
  --
  select * from (select rownum r,u.* from(select * from userinfo1 where uname like '龙%' order by id asc) u) where r>(1-1*3) and r<=2*3;
  
  
  
  
  
create or replace procedure pro_fenye(tableName varchar2,currentPage number,pageSize number,keyName varchar2,keyValue varchar2,orderName varchar2,orderWay varchar2,sumCount out number,lst out sys_refcursor)
as
       v_sql varchar2(200);
begin
  --分页查询
       v_sql := 'select * from (select rownum r,u.* from(select * from '|| tableName ||' where '|| keyName ||' like '''|| keyValue ||''' order by '|| orderName ||' '|| orderWay ||') u) where r>'||(currentPage-1)*pageSize||' and r<='|| currentPage*pageSize'';

     open lst for v_sql;
     
     --统计总条数
     
    v_sql := 'select cpunt(1) form '||tableName||'  where '|| keyName||' like '''||keyValue||'''';
     
     execute immediate v_sql into sumcount;
  end;
 
  
--创建存储过程   ：排序  分页的查询 和统计总条数 
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
   --分页查询
   
  v_sql:='select id,name from (select rownum rn,u.* from '||tableName||' u where rownum<='||currentPage||'*'||pageSize||') n where n.rn>('||currentPage||'-1)*'||pageSize||'';
   
   open lst for v_sql;
   
   --统计总条数
   
   v_sql:='select count(1) from '||tableName;
   
   execute immediate v_sql into sumCount;
end;


  
 
  drop procedure   pro_fenye;
  
  
