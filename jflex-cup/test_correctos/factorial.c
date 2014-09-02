
class Factorial{

int factorial(int n){

	int i;
	int total;
        i = n;
        total = 1;
	while (i > 0 ){
		total = total * i;
		i = i - 1;
	}

	return total;
}

int main(){
    externinvk("printf", int , factorial(3));
}

}
