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
					console.log(ctxPath);
					
					
					// 使用
		  	        require(
		  	            [
		  	                'echarts',
		  	                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
		  	                'echarts/chart/pie'
		  	            ],
		  	            function (ec) {
		  	            	//console.log(ec);
		  	                // 基于准备好的dom，初始化echarts图表
		  	                var all_count_charts = ec.init(document.getElementById('main')); 
		  	                var div_comprehensive = ec.init(document.getElementById('div_comprehensive')); 
		  	                var div_natural = ec.init(document.getElementById('div_natural')); 
		  	                var div_history = ec.init(document.getElementById('div_history')); 
		  	                var div_society = ec.init(document.getElementById('div_society')); 
		  	                var div_answer_record = ec.init(document.getElementById('div_answer_record')); 
		  	                
							//获取问答题库总数
							$.getJSON(ctxPath+"/know_statistics/getData",function(jsondata){
						  		if(jsondata.code=='200'){
						  		   console.log(JSON.stringify(jsondata));
						  		 
						  		   var main_option = {
					  	                    tooltip: {
					  	                        show: true
					  	                    },
					  	                    legend: {
					  	                        data:['各类问答题库总数']
					  	                    },
					  	                    xAxis : [
					  	                        {
					  	                            type : 'category',
					  	                            data : ["综合类问答题库总数","生鲜类问答题库总数","外贸类问答题库总数","电商类问答题库总数"]
					  	                        }
					  	                    ],
					  	                    yAxis : [
					  	                        {
					  	                            type : 'value'
					  	                        }
					  	                    ],
					  	                    series : [
					  	                        {
					  	                            "name":"",
					  	                            "type":"bar",
					  	                            "data":jsondata.data_set
					  	                        }
					  	                    ]
					  	                };
						  		 	// 为echarts对象加载数据 
				  	                all_count_charts.setOption(main_option); 
				  	                
				  	                
				  	                
				  	              comprehensive_option = {
				  	            	    title : {
				  	            	        text: '',
				  	            	        subtext: '',
				  	            	        x:'center'
				  	            	    },
				  	            	    tooltip : {
				  	            	        trigger: 'item',
				  	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
				  	            	    },
				  	            	    legend: {
				  	            	        type: 'scroll',
				  	            	        orient: 'vertical',
				  	            	        right: 10,
				  	            	        top: 20,
				  	            	        bottom: 20,
				  	            	        data: ["文本","网址","图片"],
				  	            	        
				  	            	        selected:{文本: true, 网址: true,图片: true}
				  	            	    },
				  	            	    series : [
				  	            	        {
				  	            	            name: '',
				  	            	            type: 'pie',
				  	            	            radius : '55%',
				  	            	            center: ['40%', '50%'],
				  	            	            data: jsondata.comprehensive_data,
				  	            	            itemStyle: {
				  	            	                emphasis: {
				  	            	                    shadowBlur: 10,
				  	            	                    shadowOffsetX: 0,
				  	            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				  	            	                }
				  	            	            }
				  	            	        }
				  	            	    ]
				  	            	};
				  	              div_comprehensive.setOption(comprehensive_option); 
				  	              
				  	            natural_option = {
				  	            	    title : {
				  	            	        text: '',
				  	            	        subtext: '',
				  	            	        x:'center'
				  	            	    },
				  	            	    tooltip : {
				  	            	        trigger: 'item',
				  	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
				  	            	    },
				  	            	    legend: {
				  	            	        type: 'scroll',
				  	            	        orient: 'vertical',
				  	            	        right: 10,
				  	            	        top: 20,
				  	            	        bottom: 20,
				  	            	        data: ["文本","网址","图片"],
				  	            	        
				  	            	        selected:{文本: true, 网址: true,图片: true}
				  	            	    },
				  	            	    series : [
				  	            	        {
				  	            	            name: '',
				  	            	            type: 'pie',
				  	            	            radius : '55%',
				  	            	            center: ['40%', '50%'],
				  	            	            data: jsondata.natural_data,
				  	            	            itemStyle: {
				  	            	                emphasis: {
				  	            	                    shadowBlur: 10,
				  	            	                    shadowOffsetX: 0,
				  	            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				  	            	                }
				  	            	            }
				  	            	        }
				  	            	    ]
				  	            	};
				  	              div_natural.setOption(natural_option); 
				  	              
				  	            history_option = {
				  	            	    title : {
				  	            	        text: '',
				  	            	        subtext: '',
				  	            	        x:'center'
				  	            	    },
				  	            	    tooltip : {
				  	            	        trigger: 'item',
				  	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
				  	            	    },
				  	            	    legend: {
				  	            	        type: 'scroll',
				  	            	        orient: 'vertical',
				  	            	        right: 10,
				  	            	        top: 20,
				  	            	        bottom: 20,
				  	            	        data: ["文本","网址","图片"],
				  	            	        
				  	            	        selected:{文本: true, 网址: true,图片: true}
				  	            	    },
				  	            	    series : [
				  	            	        {
				  	            	            name: '',
				  	            	            type: 'pie',
				  	            	            radius : '55%',
				  	            	            center: ['40%', '50%'],
				  	            	            data: jsondata.history_data,
				  	            	            itemStyle: {
				  	            	                emphasis: {
				  	            	                    shadowBlur: 10,
				  	            	                    shadowOffsetX: 0,
				  	            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				  	            	                }
				  	            	            }
				  	            	        }
				  	            	    ]
				  	            	};
				  	              div_history.setOption(history_option); 
				  	              
				  	            society_option = {
				  	            	    title : {
				  	            	        text: '',
				  	            	        subtext: '',
				  	            	        x:'center'
				  	            	    },
				  	            	    tooltip : {
				  	            	        trigger: 'item',
				  	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
				  	            	    },
				  	            	    legend: {
				  	            	        type: 'scroll',
				  	            	        orient: 'vertical',
				  	            	        right: 10,
				  	            	        top: 20,
				  	            	        bottom: 20,
				  	            	        data: ["文本","网址","图片"],
				  	            	        
				  	            	        selected:{文本: true, 网址: true,图片: true}
				  	            	    },
				  	            	    series : [
				  	            	        {
				  	            	            name: '',
				  	            	            type: 'pie',
				  	            	            radius : '55%',
				  	            	            center: ['40%', '50%'],
				  	            	            data: jsondata.society_data,
				  	            	            itemStyle: {
				  	            	                emphasis: {
				  	            	                    shadowBlur: 10,
				  	            	                    shadowOffsetX: 0,
				  	            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				  	            	                }
				  	            	            }
				  	            	        }
				  	            	    ]
				  	            	};
				  	              div_society.setOption(society_option); 
				  	              
				  	              
				  	            var record_option = {
				  	                    tooltip: {
				  	                        show: true
				  	                    },
				  	                    legend: {
				  	                        data:['各类问答题库总数']
				  	                    },
				  	                    xAxis : [
				  	                        {
				  	                            type : 'category',
				  	                            data : ["文本类答案回答总数","网址类答案回答总数","图片类答案回答总数","默认答案回答总数"]
				  	                        }
				  	                    ],
				  	                    yAxis : [
				  	                        {
				  	                            type : 'value'
				  	                        }
				  	                    ],
				  	                    series : [
				  	                        {
				  	                            "name":"销量",
				  	                            "type":"bar",
				  	                            "data":jsondata.record_data
				  	                        }
				  	                    ]
				  	                };
					  		 	// 为echarts对象加载数据 
				  	            div_answer_record.setOption(record_option); 
						  	        
								}else{

								}
						  	});
		  	                
		  	        
		  	                
		  	            }
		  	        );
				
					
					
					
					
					
					
				})
