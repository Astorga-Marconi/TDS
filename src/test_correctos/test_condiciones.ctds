class test_condiciones{

float dados (int d1, int d2, int d3){
	if (!(d1 == 6) && !(d2==6) && !(d3==6)){
		return 1.00;
	}
	if ( ( (d1==6) && !(d2==6) && !(d3==6)) ||
	    (!(d1==6) &&  (d2==6) && !(d3==6)) ||
	    (!(d1==6) && !(d2==6) &&  (d3==6)) ){
		return 4.00;
	}
	if (( (d1==6) &&  (d2==6) && !(d3==6)) ||
	    ( (d1==6) && !(d2==6) &&  (d3==6)) ||
	    (!(d1==6) &&  (d2==6) &&  (d3==6)) ) {
		return 8.50;
	}
	if ((d1==6) && (d2==6) && (d3==6)){
		return 10.00;
	}
}

int main(){
    externinvk("printf", float , dados(1,6,9 ));
}

}
