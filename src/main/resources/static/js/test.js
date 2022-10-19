//익명함수 테스트 그냥 () => 일때랑 function () {} 일때
//화살표 쓰게되면 전역에 만들어놓고 참조하는 형식이라서 호출한 시점에서 전역변수를 가져온다.
//즉 함수 안에서, 객체 안에서 참조변수 사용할 때 화살표 함수 쓰면 안된다. function으로 정의해줘야
const user = {
    username: "mungi",
    printUsername: function(){
        console.log(this.username);
        const testPrint = () => {
            console.log("test print: " + this.username);
        }
        testPrint(); //객체안에서 일어났기 때문에
    }

}

var username = "test"; //var를 쓰면 참조할 수 있음
user.printUsername(); //바깥에서 호출이 일어났기 때문에 var 변수의 username이 print 됨