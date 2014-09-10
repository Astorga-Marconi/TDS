
class test_Asignaciones{

int pruAritmetica(int c){
	
		int a;
		int b;
                a = 1;
                b = 8;
		a = 699 + a ;
		b = b + 2; 
		a = ( a / c / b / 2 );
		c = a % 2;
		return c;	
}

int main(){
    externinvk("printf", int , pruAritmetica(3));
}

}
