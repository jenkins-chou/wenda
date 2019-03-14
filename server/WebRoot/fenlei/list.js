var reloadList = function() {
	layui.use([ 'layer' ], function() {
		var $ = layui.juery, layer = layui.layer;
		layer.closeAll();
		loadList(true);
	});
}
layui.use('layer', function() {
	var $ = layui.juery, layer = layui.layer;
	var index = '';
	reloadList(true);

});
var resetSwClose = function(isClose) {
	layui.use([ 'layer' ], function() {
		var $ = layui.jquery, layer = layui.layer;// 独立版的layer无需执行这一句
		if (isClose) {
			$('.layui-layer-setwin a.layui-layer-close1').hide();
		} else {
			$('.layui-layer-setwin a.layui-layer-close1').show();
		}
	});
}

var loadList = function(first) {
	layui.config({
				base : "js/"
			})
			.use([ 'form', 'layer', 'jquery', 'laypage', 'table' ],
					function() {
						var form = layui.form, layer = layui.layer, laypage = layui.laypage, $ = layui.jquery;
						var table = layui.table;
						function getUParam(name, id) {
							var reg = new RegExp("(^|&)" + name
									+ "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
							var r = decodeURIComponent(
									$("#" + id).attr("src").substr(
											$("#" + id).attr("src")
													.indexOf("?")).substr(1))
									.match(reg); // 匹配目标参数
							if (r != null)
								return unescape(r[2]);
							return ""; // 返回参数值
						}
						var ctxPath = getUParam("ctx", "listjs");
						// 第一个实例
						table.render({
							elem : '#list',
							url : ctxPath + '/fenlei/list' // 数据接口
							,
							cols : [ [ // 表头
							{
								type : 'checkbox',
								fixed : 'left'
							}, {
								field : 'id',
								title : 'ID',
								sort : true,
								fixed : 'left'
							}, {
								field : 'name',
								title : '名称'
							}, {
								fixed : 'right',
								title : '操作',
								width : 160,
								templet : '#listtable-opt',
								align : 'center'
							} ] ],
							page : true,
							limits : [ 40, 60, 80, 100, 120, 140 ],
							height : 'full-80'
						// 开启分页
						});
						// 添加文章
						$(". ").click(function() {

						});
						// 监听工具条
						table.on('tool(listtable)', function(obj) {
							var that = this;
							var data = obj.data;
							var idDatas="id="+data.id;
							if (obj.event === 'del') {
								layer.confirm('你确认删除这条数据吗?', function(index) {
									$.getJSON(ctxPath + "/fenlei/del",
											idDatas, function(jsondata) {
												if (jsondata.code == '200') {
													layer.msg('删除数据成功', {
														time : 1000,
														shade : [ 0.001,
																'#ffffff' ]
													}, function() {
														reloadList();
													});
												} else {
													layer.msg(jsondata.msg, {
														time : 2000
													});
												}
											});
								});
							} else if (obj.event === 'edit') {
								if ($(that).attr("disabled") == "disabled")
									return;
								// 执行重载
								layer.open({
									title : [ '修改信息' ],
									type : 2,
									area : [ '800px', '700px' ],
									shade : [ 0.7, '#d0d7f6' ],
									scrollbar : true,
									maxmin : false,
									fixed : true,
									move : false,
									content : [
											ctxPath + '/fenlei/toget?id='
													+ data.id, 'no' ],
									end : function() {
									}
								});
							}else if(obj.event == 'pl'){
								if ($(that).attr("disabled") == "disabled")
									return;
								// 执行重载
								layer.open({
									title : [ '评论信息' ],
									type : 2,
									area : [ '100%', '100%' ],
									shade : [ 0.7, '#d0d7f6' ],
									scrollbar : true,
									maxmin : false,
									fixed : true,
									move : false,
									content : [
											ctxPath + '/pl/tolist', 'no' ],
									end : function() {
									}
								});
							}
						});

						var $ = layui.$, active = {
							add : function() { // 获取选中数据
								index = layer.open({
									title : "添加",
									type : 2,
									area : [ '100%', '100%' ],
									content : ctxPath + "/fenlei/toadd"
								});

							},
							dels : function() { // 获取选中数目
								var checkStatus = table.checkStatus('list'), data = checkStatus.data;
								var len = data.length;
								var idDatas = "";
								for ( var i = 0, l = len; i < l; i++) {
									if (i == 0) {
										idDatas += "id=" + data[i].id
									} else {
										idDatas += "&id=" + data[i].id
									}
								}
								if (len == 0) {
									layer.msg('请选择您将要删除的记录', {
										time : 2000
									});
									return false;
								} else {
									var info = '些';
									if (len == 1)
										info = '条';
									layer
											.confirm(
													'你确认删除这' + info + '记录吗？',
													{
														btn : [ '确认', '取消' ]
													// 按钮
													},
													function(index) {
														$
																.getJSON(
																		ctxPath
																				+ "/fenlei/del",
																		idDatas,
																		function(
																				jsondata) {
																			if (jsondata.code == '200') {
																				layer
																						.msg(
																								'删除数据成功',
																								{
																									time : 1000,
																									shade : [
																											0.001,
																											'#ffffff' ]
																								},
																								function() {
																									reloadList();
																								});
																			} else {
																				layer
																						.msg(
																								jsondata.msg,
																								{
																									time : 2000
																								});
																			}
																		});
													});
								}
							},
							del : function() { // 验证是否全选
								var checkStatus = table.checkStatus('idTest');
								layer.msg(checkStatus.isAll ? '全选' : '未全选')
							}
						};
						$('.layui-btn').on('click', function() {
							var type = $(this).data('type');
							active[type] ? active[type].call(this) : '';
						});
						// 全选
						form.on('checkbox(allChoose)',
										function(data) {
											var child = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"])');
											child
													.each(function(index, item) {
														item.checked = data.elem.checked;
													});
											form.render('checkbox');
										});

						// 通过判断文章是否全部选中来确定全选按钮是否选中
						form.on("checkbox(choose)",
										function(data) {
											var child = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"])');
											var childChecked = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"]):checked')
											if (childChecked.length == child.length) {
												$(data.elem)
														.parents('table')
														.find(
																'thead input#allChoose')
														.get(0).checked = true;
											} else {
												$(data.elem)
														.parents('table')
														.find(
																'thead input#allChoose')
														.get(0).checked = false;
											}
											form.render('checkbox');
										});
					});
}

// layui.config({base :
// "js/"}).use(['form','layer','jquery','laypage'],function(){
// var form = layui.form,
// layer = parent.layer === undefined ? layui.layer : parent.layer,
// laypage = layui.laypage,
// $ = layui.jquery;
//
// //加载页面数据
// var newsData = '';
// function getUParam(name,id) {
// var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
// var r =
// decodeURIComponent($("#"+id).attr("src").substr($("#"+id).attr("src").indexOf("?")).substr(1)).match(reg);
// //匹配目标参数
// if (r != null) return unescape(r[2]); return ""; //返回参数值
// }
//	
//	
// $.get(ctxPath+"/fenlei/list", function(data){
// newsData=data;
// //执行加载数据的方法
// newsList();
// })
//
//	
// //添加文章
// $(".newsAdd_btn").click(function(){
// var index = layui.layer.open({
// title : "添加文章",
// type : 2,
// content : ctxPath+"/fenlei/toadd",
// success : function(layero, index){
// layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
// tips: 3
// });
// }
// })
// //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
// $(window).resize(function(){
// layui.layer.full(index);
// })
// layui.layer.full(index);
// })
//
//	
//
// //批量删除
// $(".batchDel").click(function(){
// var $checkbox = $('.news_list tbody input[type="checkbox"][name="checked"]');
// var $checked = $('.news_list tbody
// input[type="checkbox"][name="checked"]:checked');
// if($checkbox.is(":checked")){
// layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
// var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
// setTimeout(function(){
// //删除数据
// for(var j=0;j<$checked.length;j++){
// for(var i=0;i<newsData.length;i++){
// if(newsData[i].newsId ==
// $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){
// newsData.splice(i,1);
// newsList(newsData);
// }
// }
// }
// $('.news_list thead input[type="checkbox"]').prop("checked",false);
// form.render();
// layer.close(index);
// layer.msg("删除成功");
// },2000);
// })
// }else{
// layer.msg("请选择需要删除的文章");
// }
// })
//
// //全选
// form.on('checkbox(allChoose)', function(data){
// var child = $(data.elem).parents('table').find('tbody
// input[type="checkbox"]:not([name="show"])');
// child.each(function(index, item){
// item.checked = data.elem.checked;
// });
// form.render('checkbox');
// });
//
// //通过判断文章是否全部选中来确定全选按钮是否选中
// form.on("checkbox(choose)",function(data){
// var child = $(data.elem).parents('table').find('tbody
// input[type="checkbox"]:not([name="show"])');
// var childChecked = $(data.elem).parents('table').find('tbody
// input[type="checkbox"]:not([name="show"]):checked')
// if(childChecked.length == child.length){
// $(data.elem).parents('table').find('thead input#allChoose').get(0).checked =
// true;
// }else{
// $(data.elem).parents('table').find('thead input#allChoose').get(0).checked =
// false;
// }
// form.render('checkbox');
// })
//
// //是否展示
// form.on('switch(isShow)', function(data){
// var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
// setTimeout(function(){
// layer.close(index);
// layer.msg("展示状态修改成功！");
// },2000);
// })
// 
// //操作
// $("body").on("click",".news_edit",function(){ //编辑
// layer.alert('您点击了文章编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。',{icon:6,
// title:'文章编辑'});
// })
//
// $("body").on("click",".news_collect",function(){ //收藏.
// if($(this).text().indexOf("已收藏") > 0){
// layer.msg("取消收藏成功！");
// $(this).html("<i class='layui-icon'>&#xe600;</i> 收藏");
// }else{
// layer.msg("收藏成功！");
// $(this).html("<i class='iconfont icon-star'></i> 已收藏");
// }
// })
//
// $("body").on("click",".news_del",function(){ //删除
// var _this = $(this);
// layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
// //_this.parents("tr").remove();
// for(var i=0;i<newsData.length;i++){
// if(newsData[i].newsId == _this.attr("data-id")){
// newsData.splice(i,1);
// newsList(newsData);
// }
// }
// layer.close(index);
// });
// })
//
// function newsList(that){
// //渲染数据
// function renderDate(data,curr){
// var dataHtml = '';
// if(!that){
// currData = newsData.concat().splice(curr*nums-nums, nums);
// }else{
// currData = that.concat().splice(curr*nums-nums, nums);
// }
// if(currData.length != 0){
// for(var i=0;i<currData.length;i++){
// dataHtml += '<tr>'
// +'<td><input type="checkbox" name="checked" lay-skin="primary"
// lay-filter="choose"></td>'
// +'<td align="left">'+currData[i].msg+'</td>'
// +'<td>'+currData[i].date+'</td>'
// +'<td>'
// + '<a class="layui-btn layui-btn-mini news_edit"><i class="iconfont
// icon-edit"></i> 编辑</a>'
// + '<a class="layui-btn layui-btn-danger layui-btn-mini news_del"
// data-id="'+data[i].newsId+'"><i class="layui-icon">&#xe640;</i> 删除</a>'
// +'</td>'
// +'</tr>';
// }
// }else{
// dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
// }
// return dataHtml;
// }
//
// // //分页
// // var nums = 13; //每页出现的数据量
// // if(that){
// // newsData = that;
// // }
// // laypage({
// // cont : "page",
// // pages : Math.ceil(newsData.length/nums),
// // jump : function(obj){
// // $(".news_content").html(renderDate(newsData,obj.curr));
// // $('.news_list thead input[type="checkbox"]').prop("checked",false);
// // form.render();
// // }
// // })
// }
// })
