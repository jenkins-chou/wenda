layui
		.config({
			base : "js/"
		})
		.use(
				[ 'form', 'layer', 'jquery', 'layedit', 'laydate', 'upload' ],
				function() {
					var form = layui.form, layer = layui.layer, laypage = layui.laypage, layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery, upload = layui.upload;
					function getUParam(name, id) {
						var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
						var r = decodeURIComponent(
								$("#" + id).attr("src").substr(
										$("#" + id).attr("src").indexOf("?"))
										.substr(1)).match(reg); // 匹配目标参数
						if (r != null)
							return unescape(r[2]);
						return ""; // 返回参数值
					}
					var ctxPath = getUParam("ctx", "addjs");
					var id = getUParam("id", "addjs");
					

					// 监听提交
					form.on('submit(adddd)', function(data) {
						parent.resetSwClose(true);
						layer.msg('正在保存。。。', {time : 1000},function(){});
						var url="";
						if(id!=null&&id!='')
							url=ctxPath+"/fenlei/updates";
						else
							url=ctxPath+"/fenlei/add";
						$.getJSON(url, data.field,
								function(data) {
									if (data.code == '200') {
										parent.layer.closeAll();
										parent.reloadList();
									} else {
										layer.msg('数据保存失败', {
											time : 1000
										},function(){
											alert(300)
										});
										parent.resetSwClose(false);
									}
								});
						return false;
					});
					//获取信息
					if(id!=null&&id!=''){
						$.getJSON(ctxPath+"/fenlei/get","id="+id,function(jsondata){
					  		if(jsondata.code=='200'){
					  		   console.log(JSON.stringify(jsondata.rd));
					  		  //表单初始赋值
					  		  form.val('formtable', JSON.parse(JSON.stringify(jsondata.rd)));
							}else{
								$("#subpost").attr("disabled","disabled").addClass("layui-btn-disabled");
								layer.msg(jsondata.msg,{time:2000},function(){
									parent.layer.closeAll('iframe');
								});
							}
					  	});
					}
					

				})
