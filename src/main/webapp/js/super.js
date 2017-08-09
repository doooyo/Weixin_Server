/*easyui样式初始化*/
$.fn.tabs.defaults.tabHeight = 32; //tab标签条高度
// $.fn.linkbutton.defaults.height = 32; //按钮高度
$.fn.menu.defaults.itemHeight = 28; //menu高度

$.fn.validatebox.defaults.height = 32;
$.fn.textbox.defaults.height = 32;
$.fn.textbox.defaults.iconWidth = 32;
$.fn.datebox.defaults.height = 32;
$.fn.numberspinner.defaults.height = 32;
$.fn.timespinner.defaults.height = 32;
$.fn.numberbox.defaults.height = 32;
$.fn.combobox.defaults.height = 32;
$.fn.passwordbox.defaults.height = 32;
$.fn.filebox.defaults.height = 32;

$.fn.menu.defaults.noline = true
$.fn.progressbar.defaults.height = 18; //进度条

// $(function() {

// 	/*左侧导航分类选中样式*/
// 	$(".panel-body .accordion-body>ul>li").on('click', function() {
// 		$(this).siblings().removeClass('super-accordion-selected');
// 		$(this).addClass('super-accordion-selected');

// 		//新增一个选项卡
// 		var tabUrl = $(this).data('url');
// 		var tabTitle = $(this).text();
// 		//tab是否存在
// 		if($("#tt").tabs('exists', tabTitle)) {
// 			$("#tt").tabs('select', tabTitle);
// 		} else {
// 			$('#tt').tabs('add', {
// 				title: tabTitle,
// 				href: tabUrl,
// 				closable: true
// 			});
// 		}
// 	});

// 	/*设置按钮的下拉菜单*/
// 	$('.super-setting-icon').on('click', function() {
// 		$('#mm').menu('show', {
// 			top: 50,
// 			left: document.body.scrollWidth - 130
// 		});
// 	});

// 	/*修改主题*/
// 	$('#themeSetting').on('click', function() {
// 		var themeWin = $('#win').dialog({
// 			width: 460,
// 			height: 260,
// 			modal: true,
// 			title: '主题设置',
// 			buttons: [{
// 				text: '保存',
// 				id: 'btn-sure',
// 				handler: function() {
// 					themeWin.panel('close');
// 				}
// 			}, {
// 				text: '关闭',
// 				handler: function() {
// 					themeWin.panel('close');
// 				}
// 			}],
// 			onOpen: function() {
// 				$(".themeItem").show();
// 			}
// 		});
// 	});
// 	$(".themeItem ul li").on('click', function() {
// 		$(".themeItem ul li").removeClass('themeActive');
// 		$(this).addClass('themeActive');
// 	});

// 	/*退出系统*/
// 	$("#logout").on('click', function() {
// 		$.messager.confirm('提示', '确定退出系统？', function(r) {
// 			if(r) {
// 				console.log('确定退出')
// 			}
// 		});
// 	});
// });

$(function() {
	//当前登陆账户
	var loginUser = Cookies.get('userName');
	$('.loginUser').text(loginUser);

	/*左侧菜单折叠*/
	$(".expandSidebar").on("click",function(){
		//collapse,expand panel
		var isVisible = $('#main').layout('panel','west').is(':visible');
		var method = 'expand';
		if(isVisible){
			method = 'collapse';
		}	
		$('#main').layout(method,'west');//collapse,expand	
	});

	/*设置按钮的下拉菜单*/
	$('.super-setting-icon').on('click', function() {
		$('#mm').menu('show', {
			top: 50,
			left: document.body.scrollWidth - 130
		});
	});

	/*修改主题*/
	/*$('#themeSetting').on('click', function() {
		var themeWin = $('#win').dialog({
			width: 460,
			height: 260,
			modal: true,
			title: '主题设置',
			buttons: [{
				text: '保存',
				id: 'btn-sure',
				handler: function() {
					themeWin.panel('close');
				}
			}, {
				text: '关闭',
				handler: function() {
					themeWin.panel('close');
				}
			}],
			onOpen: function() {
				$(".themeItem").show();
			}
		});
	});*/
	$('#modifyPwd').on('click', function() {
		$('#pwdForm').form('clear');
		var themeWin = $('#changePwd').dialog({
			modal: true,
			closed: false,
			title: '密码修改',
			buttons: [{
				text: '确定',
				id: 'btn-sure',
				handler: function() {
					themeWin.panel('close');
				}
			}, {
				text: '取消',
				handler: function() {
					themeWin.panel('close');
				}
			}],
			onOpen: function() {
				//$(".themeItem").show();
			}
		});
		 $('#changePwd').window('center');
	});

	// $(".themeItem ul li").on('click', function() {
	// 	$(".themeItem ul li").removeClass('themeActive');
	// 	$(this).addClass('themeActive');
	// });

	/*退出系统*/
	$("#logout").on('click', function() {
		$.messager.confirm('提示', '确定退出系统？', function(r) {
			if(r) {
				var url = API.logout;
				whayer.post(url,{},function(res){
					logout();
				});				
			}
		});
	});
	
	function logout(){
		Cookies.remove('userName');
		window.location.href = 'login.html';
	}
	/*菜单单击*/
	$("#easyui-layout-west .easyui-accordion ul>li").on('click', function() {
		$(this).siblings().removeClass('super-accordion-selected');
		$(this).addClass('super-accordion-selected');

		var title = $(this).text();
		var url = $(this).attr('data-url');
		var iconCls = $(this).attr('data-icon');
		//var iframe = $(this).attr('data-iframe')==1?true:false;
		var iframe = false;
		addTab(title,url,iconCls,iframe);
	});
	/**
	* Name 添加菜单选项
	* Param title 名称
	* Param href 链接
	* Param iconCls 图标样式
	* Param iframe 链接跳转方式（true为iframe，false为href）
	*/	
	function addTab(title, href, iconCls, iframe){
		var tabPanel = $('#whayerTabs');
		if(!tabPanel.tabs('exists',title)){
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
			if(iframe){
				tabPanel.tabs('add',{
					title:title,
					content:content,
					iconCls:iconCls,
					fit:true,
					cls:'pd5',
					closable:true
				});
			}
			else{
				tabPanel.tabs('add',{
					title:title,
					href:href,
					iconCls:iconCls,
					fit:true,
					cls:'pd5',
					closable:true
				});
			}
		}
		else
		{
			tabPanel.tabs('select',title);
		}
	}

	/*tab右键菜单*/
	$('#whayerTabs').tabs({
        onContextMenu:function(e, title,index){
            e.preventDefault();
            if(index>0){
                $('#tabContextMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);
            }
        }
    });

	//右键菜单click
    $("#tabContextMenu").menu({
        onClick : function (item) {
            closeTab(this, item.name);
        }
    });

     //删除Tabs
    function closeTab(menu, type){
        var allTabs = $("#whayerTabs").tabs('tabs');
        var allTabtitle = [];
        $.each(allTabs,function(i,n){
            var opt=$(n).panel('options');
            if(opt.closable)
                allTabtitle.push(opt.title);
        });

        switch (type){
            case 1 ://关闭当前
                var curTabTitle = $(menu).data("tabTitle");
                $("#whayerTabs").tabs("close", curTabTitle);
            	break;
            case 2 ://关闭所有
                for(var i=0;i<allTabtitle.length;i++){
                    $('#whayerTabs').tabs('close', allTabtitle[i]);
                }
            	break;
            case 3 ://关闭其他
            	 var curTabTitle = $(menu).data("tabTitle");
            	for(var i=0;i<allTabtitle.length;i++){
            		if(allTabtitle[i]!=curTabTitle){
            			$('#whayerTabs').tabs('close', allTabtitle[i]);
            		}                    
                }                
            	break;
            case 4 ://刷新
        		var currentTab = $('#whayerTabs').tabs('getSelected');  // get selected panel
				var url = $(currentTab.panel('options')).attr('href');
		        $('#tabs').tabs('update', {
		            tab: currentTab,
		            options: {
		                href: url
		            }
		        });
		        currentTab.panel('refresh');
            	break;
            default :        
            	break;
        }
        
    }
});

$.parser.onComplete = function() {
	//$("#index").css('opacity', 1);
}

/**
 * 初始化示例
 */
// function initDemo() {
// 	/*初始化示例div*/
// 	var demoPanelId = 'demoPanel' + (new Date()).getTime();
// 	$('#demoPanel').attr('id', demoPanelId);
// 	var demoPaneCodeId = 'demoPanelCode' + (new Date()).getTime();
// 	$('#demoPanelCode').attr('id', demoPaneCodeId);

// 	/*示例导航选中样式*/
// 	$(".demo-list>ul>li").on('click', function() {
// 		$('#et-demo').tabs('select', '预览');

// 		$(this).siblings().removeClass('super-accordion-selected');
// 		$(this).addClass('super-accordion-selected');
// 		//加载页面
// 		$('#' + demoPanelId).panel('open').panel('refresh', $(this).data('url'));
// 	});
// }

