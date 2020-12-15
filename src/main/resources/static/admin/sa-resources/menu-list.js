// 一个菜单可以包括的所有属性 
// {
// 	id: '12345',		// 菜单id, 必须唯一
// 	name: '用户中心',		// 菜单名称, 同时也是tab选项卡上显示的名称
// 	icon: 'el-icon-user',	// 菜单图标, 参考地址:  https://element.eleme.cn/#/zh-CN/component/icon
//	info: '管理所有用户',	// 菜单介绍, 在菜单预览和分配权限时会有显示 
// 	url: 'sa-html/user/user-list.html',	// 菜单指向地址
// 	parent_id: 1,			// 所属父菜单id, 如果指定了一个值, sa-admin在初始化时会将此菜单转移到指定菜单上 
// 	is_show: true,			// 是否显示, 默认true
// 	is_blank: false,		// 是否属于外部链接, 如果为true, 则点击菜单时从新窗口打开 
// 	childList: [			// 指定这个菜单所有的子菜单, 子菜单可以继续指定子菜单, 至多支持三级菜单
// 		// .... 
// 	],
//	click: function(){}		// 点击菜单执行一个函数 
// }

// 定义菜单列表 
var menuList = [
    // {
    // 	id: '131231',
    // 	name: '文档说明',
    // 	info: 'sa-admin使用文档',
    // },
    {
        id: '1',
        name: '用户管理',
        icon: 'el-icon-user',
        info: '对用户列表、添加、统计等等...',
        childList: [
            {id: '1-1', name: '用户列表', icon: 'el-icon-document', url: 'sa-html/user/user-list.html'},
            {id: '1-2', name: '用户添加', icon: 'el-icon-plus', url: 'sa-html/user/user-add.html'},

        ]
    },

    {
        id: '2',
        name: '卡密管理',
        icon: 'el-icon-key',
        info: '管理卡密',
        childList: [
            {id: '2-1', name: '卡密列表', icon: 'el-icon-document', url: 'sa-html/cdk/cdk-list.html'},
            {id: '2-2', name: '卡密添加', icon: 'el-icon-plus', url: 'sa-html/cdk/cdk-add.html'},
            {id: '2-3', name: '卡密生成', icon: 'el-icon-position', url: 'sa-html/cdk/cdk-rand.html'},
        ]
    },
    {
        id: '3',
        name: '手动填报',
        icon: 'el-icon-thumb',
        info: '手动填报',
        url: 'sa-html/tool/docheck.html'
    },
        {
        id: '4',
        name: '发送公告',
        icon: 'el-icon-bell',
        info: '发送公告',
        url: 'sa-html/tool/sendnotice.html'
    },
]