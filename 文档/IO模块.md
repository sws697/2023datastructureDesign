# IO模块

- 该模块需要将学生和管理员按一定的格式输入字符串,将字符串识别为对应的指令,并将指令传递给相应的模块进行处理。
同时该模块也提供相应模块的输出接口,供其他模块调用。

## 1. 输入

- ### 用户系统指令

    - 用户登陆指令: `login [usertype] [username] [password]`
    - 用户登出指令: `logout`
    - 用户注册指令: `register [usertype] [username] [password]`
    - 用户删除指令: `delete [usertype] [username]`
  
- ### 日程管理系统指令
    - #### 课程管理系统
      - 课程查询指令: `query [course_name]`
    - #### 课外活动管理系统
       - 课外活动添加指令: `add_activity [activity_type] [activity_cycle_type] [activity_name] [activity_begin_time] [activity_end_time] [activity_place]`
       - 课外活动删除指令: `delete_activity [activity_name]`
       - 课外活动查询指令: `query_activity [activity_name]`\ `query_label [activity_type]` \ `query_time [activity_begin_time] [activity_end_time](可选)`（表示在这一时间段内有什么课外活动）
       - 课外活动修改指令: `modify_activity [activity_name] [activity_type] [activity_cycle_type] [activity_begin_time] [activity_end_time] [activity_place]`
       - 设定闹钟指令: `set_alarm [alarm_name] [activity_name] [alarm_time] [alarm_type]`
       - 删除闹钟指令: `delete_alarm [alarm_name]`

  -  #### 临时事务管理

      - 临时事务添加指令: `add_event [event_type] [event_name] [event_begin_time] [event_place]`
      - 临时事务删除指令: `delete_event [event_name]`
      - 临时事务查询指令: `query_event [event_name]` \ `query_label [event_type]` \ `query_time [event_begin_time] [event_end_time](可选)`（表示在这一时间段内有什么临时事务）
    - #### 导航系统
      - 用户输入位置指令: `input_location [location]`
      - 用户输入日程信息自动进行路径规划指令: `input_schedule [schedule——name]`

## 2. 输出

-  ### 课程管理系统
      - 课程信息显示：`show_course (course_name)`供给课程管理模块调用
      - 课程提醒：`course_remind (course_name)`供给课程管理模块调用
- ### 课外活动管理系统
  - 课外活动信息显示：`show_activity (activity_name)`供给课外活动管理模块调用
  - 课外活动查询结果排序显示 ：`show_activity_sorted (activity[])`供给课外活动管理模块调用
  - 课外活动提醒：`activity_remind (activity_name,activity_place)`供给课外活动管理模块调用
- ### 临时事务管理系统
  - 临时事务信息显示：`show_event (event_name)`供给临时事务管理模块调用
  - 临时事务查询结果排序显示 ：`show_event_sorted (event[])`供给临时事务管理模块调用
  - 临时事务提醒：`event_remind (event_name,event_place)`供给临时事务管理模块调用
- ### 导航系统
  - 生成导航路径：`show_path (path[])`供给导航模块调用
- ### 用户系统
  - 用户登陆成功：`login_success()`供给用户管理模块调用
  - 用户登陆失败：`login_fail()`供给用户管理模块调用
  - 用户注册成功：`register_success()`供给用户管理模块调用
  - 用户注册失败：`register_fail()`供给用户管理模块调用
  - 用户删除成功：`delete_success()`供给用户管理模块调用
  - 用户删除失败：`delete_fail()`供给用户管理模块调用
  - 用户登出成功：`logout_success()`供给用户管理模块调用
  - 用户登出失败：`logout_fail()`供给用户管理模块调用
- 冲突检测 ：`conflict_announce()`