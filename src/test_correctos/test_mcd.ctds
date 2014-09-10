class test_maxcomdiv{

int maxcomdiv (int a, int b){
        int dividendo,divisor,resto;
	if(a > b){
		dividendo = a;
		divisor = b;
	}
	else{
		dividendo = b;
		divisor = a;
	}
	resto = 1;
	while ((resto != 0)){
		resto = dividendo % divisor;
		dividendo = divisor;
		divisor = resto;
	}
	return dividendo;
}

int main(){
    externinvk("printf", float , "mcd%d", maxcomdiv(6,9 ));
}

}
