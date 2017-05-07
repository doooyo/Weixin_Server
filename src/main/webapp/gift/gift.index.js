$(function(){
	var gift = {
			getUrlParam:function(){
				//获取orderId
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		        if (r != null) return unescape(r[2]); return null; //返回参数值 
			},
			init:function(){
				var _this = gift;

				//获取礼品信息
				_this.getGiftList();

				$(".submit").on("click",function(){
					_this.submit();
				});
			},
			initSwiper:function(){
				var swiper = new Swiper('.swiper-container', {
			        pagination: '.swiper-pagination',
			        paginationClickable: true,
			        nextButton: '.swiper-button-next',
			        prevButton: '.swiper-button-prev',
			        spaceBetween: 30
			    });
			},
			getGiftList:function(){
				var _this = gift;
				var url = '/gift/getList';
				var data = {
					expired:1
				};
				$.ajax({
					url:url,
					type:'GET',
					data:data,
					dateType:"json",
					success:function(res){
						if(res.isSuccess){
							$(".submit").attr("disabled",false);
							//获取礼品成功
							_this.renderGift(res.list);
						}else{

						}						
					}
				});
			},
			renderGift:function(list){
				var _this = gift;
				
				var $swiperWrapper = $(".swiper-wrapper");
		        var str = '<div class="swiper-slide">';    
				for (var i = 0; i < list.length; i++) {
					var item = list[i];
					var imageUrl = 'http://scskss.com/image/gift/'+item['imgSrc'];
					str+= '<image src="'+imageUrl+'"/>';
					str+='</div>';
				}
				$swiperWrapper.html(str);

				_this.initSwiper();
			},
			submit:function(){
				var _this = gift;
				var oderId = _this.getUrlParam('orderId');
				var data = {
					orderId:oderId,
					name:$("#username").val(),
					mobile:$("#mobile").val(),
					address:$("#address").val()
				};
				$.ajax({
					url:'/gift/saveGiftRelease',
					type:'POST',
					data:data,
					dateType:"json", 
					success:function(res){
						if(res.isSuccess){
							$(".sendTips").html('提交成功');
						}else{							
							$(".sendTips").html(res.errorMsg);
						}						
					},
					error:function(XMLHttpRequest, textStatus, errorThrow){
						
					}
				});
			},
			validate:function(){

			}
		};
		gift.init();
});