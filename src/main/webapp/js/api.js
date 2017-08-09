;(function(global){
	var proxy = false;
	var baseUrl = proxy?'/proxy':'';
	var API = {
		/*用户注册*/
		userRegister:baseUrl+"/user/register/agent",				/*用户注册*/
		validatePid:baseUrl+"/user/validatePid",					/*验证父级*/

		/*用户登录*/
		loginUlr:baseUrl+"/user/login",								/*用户登录*/
		validateUrl:baseUrl+"/user/login/verify",					/*验证码*/	

		logout:baseUrl+"/user/logout",								/*用户退出*/
		
		/*代理商*/
		getAgentListByType:baseUrl+"/user/getUserListByType",		/*获取代理商列表*/
		deleteAgent:baseUrl+"/user/deleteUser",						/*删除代理商*/
		approvalAgent:baseUrl+"/user/approval/agent",				/*置为区代*/

		/*代理商审核*/
		approvalAudit:baseUrl+"/user/approval/audit",				/*通过审核*/

		/*集团管理*/
		getCompanyList:baseUrl+"/company/getList",					/*获取集团列表*/
		addCompany:baseUrl+"/company/save",							/*新增集团用户*/
		deleteCompany:baseUrl+"/company/delete",					/*删除集团用户*/
		updateCompany:baseUrl+"/company/update",					/*更新集团用户*/

		/*产品分类*/
		getCategoryList:baseUrl+"/product2category/getList",		/*获取产品分类列表*/
		addCategory:baseUrl+"/product2category/save",				/*新增产品分类*/
		deleteCategory:baseUrl+"/product2category/delete",			/*删除产品分类*/
		updateCategory:baseUrl+"/product2category/update",			/*更新产品分类*/

		/*产品管理*/
		getProductList:baseUrl+"/product/getList",					/*获取产品列表*/
		addProduct:baseUrl+"/product/save",							/*新增产品*/
		updateProduct:baseUrl+"/product/update",					/*更新产品*/
		downProduct:baseUrl+"/product/updateOnOrOff",				/*产品下架*/

		/*产品打标*/
		getProductList2Role:baseUrl+"/product/getList2Role",		/*获取用户产品列表*/
		productAssociate:baseUrl+"/product2role/associate",			/*产品打标*/
		


		/*订单管理*/
		getOrderListByTypeV2:baseUrl+"/order/getListByTypeV2", 		/*获取订单列表*/
		saveOrder2Box:baseUrl+"/order/saveOrder2Box",         		/*绑定检测盒*/
		getOrderDetail:baseUrl+"/order/getDetailById",				/*获取订单详情*/


		/*赠品管理*/
		getGiftList:baseUrl+"/gift/getList",						/*获取礼品列表*/
		addGift:baseUrl+"/gift/save",								/*新增礼品*/
		deleteGift:baseUrl+"/gift/deleteById",						/*删除礼品*/
		updateGift:baseUrl+"/gift/update",							/*更新礼品信息*/

		/*赠品发放记录*/
		getGiftReleaseList:baseUrl+"/gift/getGiftReleaseList",		/*获取赠品发放记录列表*/
		updateGiftRelease:baseUrl+"/gift/updateGiftRelease",		/*设置为已邮寄*/

		/*微信卡券，卡券打标*/
		getRoleList:baseUrl+"/role/getList",						/*获取用户角色列表*/	
		getCardList2Role:baseUrl+"/card2role/getCardList2Role",		/*获取用户卡券列表*/
		cardAssociate:baseUrl+"/card2role/associate"				/*卡券打标*/
	};

	API.proxy = proxy;
	window.API = API;
})(window);