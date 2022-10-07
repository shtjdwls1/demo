var main= {
    init : function (){
        var _this = this;
        $('#btn-save').on('click',()=>{
            _this.save();
        });
        $('#btn-update').on('click',()=>{
            _this.update();
        });
        $('#btn-delete').on('click',()=>{
            _this.delete();
        })
    },
    save : ()=>{
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };
        axios.post('/api/v1/posts',data).then(()=>{
            alert('글이 등록되었습니다.');
            window.location.href='/';
        }).catch((error)=>{
            alert(JSON.stringify(error));
        });
    },
    update : ()=>{
        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        var id = $('#id').val();

        axios.put('/api/v1/posts/'+id,data).then(()=>{
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).catch((error)=>{
            alert(JSON.stringify(error));
        });
    },
    delete : ()=>{
        var id = $('#id').val();
        axios.delete('/api/v1/posts/'+id).then(()=>{
            alert('글이 삭제되었습니다.');
            window.location.href ='/';
        }).catch((error)=>{
            alert(JSON.stringify(error));
        })
    }
};

main.init();