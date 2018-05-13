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

    $('#pictureAmount').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#packageNum').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#payment').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#workerNum').editable({
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

    $('#gradesLimit').editable({
        validate: function(value) {
            if($.trim(value) == '') return 'This field is required';
        }
    });

    $('#markMode').editable({
        prepend: "not selected",
        source: [
            {value: 1, text: 'entirety'},
            {value: 2, text: 'tags'},
            {value: 3, text: 'rectangle'},
            {value: 4, text: 'area'}
        ],
        display: function(value, sourceData) {
            var colors = {"": "gray", 1: "green", 2: "blue",3: "navy", 4: "orange",5: "pink"},
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

    $('#tags').editable({
        inputclass: 'input-large',
        select2: {
            tags: ['html', 'javascript', 'css', 'ajax'],
            tokenSeparators: [",", " "]
        }
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