/**
 * 控制画板的逻辑,实现作画的功能
 */
var rubberSize = 12;
var pencilSize = 2;
//var canvasWidth=document.getElementById("penal").width;
//var canvasHeight=document.getElementById("penal").height;
var canvasWidth=800;
var canvasHeight=530;

function clearAllMark(){//清空画板内容
	document.getElementById("penal").getContext("2d").clearRect(0,0,canvasWidth,canvasHeight);
}

function changePencilSize(){//改变笔触大小
	"use strict";
	document.getElementById("penal").getContext("2d").lineWidth =  document.getElementById("pencilSize").value;
}

function changeRubberSize(){//改变橡皮擦大小
	"use strict";
	rubberSize = document.getElementById("rubberSize").value;
}

function changePencilColor(){//改变铅笔颜色
	"use strict";
	document.getElementById("penal").getContext("2d").strokeStyle = document.getElementById("pencilColor").value;
}

var Mark = function (){
	'use strict';
	this.isDraw = false;//开关
	this.type = "pencil";
	this.penal = document.getElementById("penal");
	this.pen = this.penal.getContext("2d");
	this.color = document.getElementById("color");
	this.lineWidth = document.getElementById("lineWidth");
	this.tools = document.getElementById("tools");
	this.img = new Image();//动态绘制矩形、圆形
	
};

Mark.prototype.init = function(){//初始化
	'use strict';
	var self = this;
	this.pen.lineWidth = pencilSize;
	var originX = null;
    var originY = null;

	
	this.tools.addEventListener('click', function(event){//工具栏点击事件
		$(".tools").css("border-style","hidden");
		
		if(event.target.id === "pencil"){
			self.type = "pencil";
			$("#pencil").css("border-style","inset");
			$("#pencil").css("border-width","medium");
			
		}
		else if(event.target.id === "line"){
			self.type = "line";
			$("#line").css("border-style","inset");
			$("#line").css("border-width","medium");
			
		}
		else if(event.target.id === "square"){
			self.type = "square";
			$("#square").css("border-style","inset");
			$("#square").css("border-width","medium");
			
		}
		else if(event.target.id === "circle"){
			self.type = "circle";
			$("#circle").css("border-style","inset");
			$("#circle").css("border-width","medium");
		
		}
		else if(event.target.id === "rubber"){
			self.type = "rubber";
			$("#rubber").css("border-style","inset");
			$("#rubber").css("border-width","medium");
			
		}
	}, false);
	
	this.penal.addEventListener('mouseleave',function(){//鼠标离开
		if(self.isDraw){
			self.pen.closePath();
			self.isDraw = false; 
		}
	}, false);
	
	this.penal.addEventListener("mouseup", function(event){//松开鼠标
		if(self.isDraw){
			self.pen.closePath();
			self.isDraw = false;
		}
	},false);
	
	this.penal.addEventListener("mousemove",function(event){//鼠标移动
		if(self.isDraw){//如果在绘画
			var x = event.offsetX;
			var y = event.offsetY;//点击事件相对于事件所属的this的位置
			
			if(self.type === "pencil"){//铅笔
				self.pen.lineTo(x,y);
				self.pen.stroke();
			}
			else if(self.type === "line"){//直线
				
				self.pen.clearRect(0,0,canvasWidth,canvasHeight);//增加代码
		        self.pen.drawImage(self.img, 0, 0);//增加代码
		        self.pen.beginPath();//增加代码
		        
		        self.pen.moveTo(self.originX,self.originY);
		        self.pen.lineTo(x,y);
		        self.pen.stroke();
		        
		        self.pen.closePath();//增加代码
			}
			else if(self.type === "square"){//方形
				
				self.pen.clearRect(0,0,canvasWidth,canvasHeight);//增加代码
		        self.pen.drawImage(self.img, 0, 0);//增加代码
		        self.pen.beginPath();//增加代码
		       
		        self.pen.strokeRect(self.originX,self.originY,x-self.originX,y-self.originY);
		       
		        self.pen.closePath();//增加代码
			}
			else if(self.type === "circle"){//圆形
				
				self.pen.clearRect(0,0,canvasWidth,canvasHeight);//增加代码
		        self.pen.drawImage(self.img, 0, 0);//增加代码
		        self.pen.beginPath();//增加代码
		        
		        self.pen.arc(self.originX,self.originY,Math.sqrt((x-self.originX)*(x-self.originX)+(y-self.originY)*(y-self.originY)),0,2*Math.PI);
		        self.pen.stroke();
		        
		        self.pen.closePath();//增加代码
				
			}
			else if(self.type === "rubber"){//橡皮
				self.pen.clearRect(x-rubberSize/2,y-rubberSize/2,rubberSize,rubberSize);
			}
			
		}
	}, false);
	
	this.penal.addEventListener("mousedown", function(event){//按下鼠标
		self.isDraw = true;
		
	    self.img.src = self.penal.toDataURL('image/png');//为了动态绘图
		self.originX = event.offsetX;
		self.originY  = event.offsetY;
	    self.pen.beginPath();
	},false);
}
