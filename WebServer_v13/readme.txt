完成修改密码业务

用户打开修改密码页面，然后输入要修改的用户名,
原密码以及新密码，之后点击修改提交内容。服务
端接收到数据后检查user.dat文件中对应用户的密码
是否与输入的原密码一致，若不一致则响应原密码输
入错误页面，否则覆盖原密码为新密码来完成修改操
作并响应修改结果。若输入的用户名不存在，则响应
查无此人页面。


             
    
    
    
    
    
    
    