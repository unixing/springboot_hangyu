

for(var i=0;i<window.parent.menus2.length;i++){
	if(parseInt(window.parent.menus2[i].type) == 0){
		$("#_permissions_"+(i+1)).css({"display":"none"})
	}
}