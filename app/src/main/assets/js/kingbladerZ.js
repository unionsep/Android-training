$(function(){

    $(".js-div").append("jquery appended");

    $("#js-alert").on("click", function(){
        alert("clicked!");
    });

    var canvas = new fabric.Canvas("canvas", {
        backgroundColor: 'rgb(100,100,200)',
        selectionColor: 'blue',
        selectionLineWidth: 2,
        width: 335,
        height: 400
    });


    fabric.Image.fromURL('img/Duke_ThumbsUp.png', function(img) {
        img.scale(0.1).set({
            left: 60,
            top: 60,
            angle: 0
        });
        canvas.add(img).setActiveObject(img);
    });

    $('#js-add_canvas').on('click', function(){
        var text = $('#js-canvas_text').val();
        if (null == text) {
            return;
        } else {
            var fText = new fabric.Text(text, {
                top: 60,
                left: 60,
                fontFamily: 'Impact',
                fontSize: 24,
                fontWeight: 'bold',
                fill: 'green'
            });
            canvas.add(fText);
        }
    });

    $('#js-save_image').on('click', function(){
        var canvas = $('#canvas')[0];
        var img_src = canvas.toDataURL('image/jpeg');
        $('#js-img_preview').attr('src', img_src);
        $('#js-img_download').attr('href', img_src);
    });

});