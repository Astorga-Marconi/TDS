class test_arreglos{

int A[589] ;
float B [56];
boolean C [5];

int pruArrreglos(int b, int c ){
	
        float res[8];
        int i;   

	if(b > c){
		res[0] = 1 - c;
        }
	
        if(b == c){
		res[1-1] = b*5;
        }
	else{
		res[b-b] = c;
        }
        
        i= 0;
        while (i < 589){
             A[i] = i *2;
             i += 1;
        }

        i=8;
        while i>= 0 {
           B[i] = A[i*2 + 8] + 3 ;
           i -= 1;
        }

        i=0;
        while i <= (5 *9)-40 {
           C[i] = A[i*2 + 8] > B[i] ;
           i = i + 1;
        }
 

	return res[0];
		
	}

int main(){

    externinvk("printf", int , pruArreglos(4,8 ));
    externinvk("printf", int);

    
}

}
