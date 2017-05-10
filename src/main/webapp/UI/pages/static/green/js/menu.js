var SystemMenu = [{
	title: '主页',
	icon: '&#xe63f;',
	isCurrent: true,
	menu: [{
		title: '订单统计',
		icon: '&#xe647;',
		isCurrent: true,
		children: [{
			title: '订单详情',
			href: 'basic_info.html',
			isCurrent: true
		}]
	},{
		title: '代理商统计',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '代理商管理',
	icon: '&#xe60d;',
	menu: [{
		title: '代理商审核',
		icon: '&#xe647;',
		href: 'index.html',
		isCurrent: true,
		children: []
	},{
		title: '代理商管理',
		icon: '&#xe611;',
		href: 'process.html',
		children: []
	}]
},{
	title: '集团管理',
	icon: '&#xe61e;',
	menu: [{
		title: '集团信息管理',
		icon: '&#xe647;',
		href: 'process.html',
		isCurrent: true,
		children: []
	}]
},{
	title: '产品管理',
	icon: '&#xe620;',
	menu: [{
		title: '产品管理',
		icon: '&#xe647;',
		href: 'basic_info.html',
		children: []
	},{
		title: '产品权限',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '订单管理',
	icon: '&#xe625;',
	menu: [{
		title: '订单管理',
		icon: '&#xe647;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '赠品管理',
	icon: '&#xe64b;',
	menu: [{
		title: '发放记录',
		icon: '&#xe647;',
		isCurrent: true,
		children: [{
			title: '自定义',
			href: 'basic_info.html',
			isCurrent: true
		},{
			title: '采购分析',
			href: 'index.html'
		}]
	},{
		title: '赠品管理',
		icon: '&#xe611;',
		href: 'index.html',
		children: []
	}]
},{
	title: '券务管理',
	icon: '&#xe64c;',
	menu: [{
		title: '代金券管理',
		icon: '&#xe647;',
		isCurrent: true,
		children: []
	},{
		title: '优惠券管理',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '会员管理',
	icon: '&#xe646;',
	menu: [{
		title: '会员管理1',
		icon: '&#xe647;',
		isCurrent: true,
		children: []
	},{
		title: '会员管理2',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '系统管理',
	icon: '&#xe646;',
	menu: [{
		title: '权限管理',
		icon: '&#xe647;',
		isCurrent: true,
		children: []
	},{
		title: '用户管理',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
}];