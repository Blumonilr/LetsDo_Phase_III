$(function(){
  
   //defaults
   $.fn.editable.defaults.url = '/post'; 

    //enable / disable
   $('#enable').click(function() {
       $('#user .editable').editable('toggleDisabled');
   });    
    
    //editables 
    $('#projectName').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#payment').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#maxNumPerPic').editable({
        prepend: "not selected",
        source: [
            {value: 1, text: '1'},
            {value: 2, text: '2'},
            {value: 3, text: '3'},
            {value: 4, text: '4'},
            {value: 5, text: '5'},
            {value: 6, text: '6'}
        ],
        display: function(value, sourceData) {
            var colors = {"": "gray", 1: "green", 2: "blue",3: "navy", 4: "orange",5: "pink", 6: "black"},
                elem = $.grep(sourceData, function(o){return o.value == value;});

            if(elem.length) {
                $(this).text(elem[0].text).css("color", colors[value]);
            } else {
                $(this).empty();
            }
        }
    });

    $('#minNumPerPic').editable({
        prepend: "not selected",
        source: [
            {value: 1, text: '1'},
            {value: 2, text: '2'},
            {value: 3, text: '3'},
            {value: 4, text: '4'},
            {value: 5, text: '5'},
            {value: 6, text: '6'}
        ],
        display: function(value, sourceData) {
            var colors = {"": "gray", 1: "green", 2: "blue",3: "navy", 4: "orange",5: "pink", 6: "black"},
                elem = $.grep(sourceData, function(o){return o.value == value;});

            if(elem.length) {
                $(this).text(elem[0].text).css("color", colors[value]);
            } else {
                $(this).empty();
            }
        }
    });

    $('#levelLimit').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#testAccuracy').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#markMode').editable({
        prepend: "not selected",
        source: [
            {value: 1, text: '框选标注'},
            {value: 2, text: '区域标注'}
        ],
        display: function(value, sourceData) {
            var colors = {"": "gray", 1: "green", 2: "blue"},
                elem = $.grep(sourceData, function(o){return o.value == value;});

            if(elem.length) {
                $(this).text(elem[0].text).css("color", colors[value]);
            } else {
                $(this).empty();
            }
        }
    });

    $('#note').editable();
    $('#pencil').click(function(e) {
        e.stopPropagation();
        e.preventDefault();
        $('#note').editable('toggle');
    });

         
   $('#user .editable').on('hidden', function(e, reason){
        if(reason === 'save' || reason === 'nochange') {
            var $next = $(this).closest('tr').next().find('.editable');
            if($('#autoopen').is(':checked')) {
                setTimeout(function() {
                    $next.editable('show');
                }, 300); 
            } else {
                $next.focus();
            } 
        }
   });
   
});