#include <iostream>
using namespace std;

int main()
{
    int data[10];
    int dataatrec[10],c,c1,c2,c3,i;

    cout<<"Enter 4 bits of data \n";
    cin>>data[7];
    cin>>data[6];
    cin>>data[5];
    cin>>data[3];


    data[4]=data[5]^data[6]^data[7];
    data[2]=data[3]^data[6]^data[7];
    data[1]=data[3]^data[5]^data[7];


    cout<<"Encoded data is \n";
    for(i=1;i<=7;i++){
        cout<<data[i];
    }

    cout<<"\n Enter encoded  data bits \n";
     for(i=1;i<=7;i++){
        cin>>dataatrec[i];
    }

    c1=dataatrec[1]^dataatrec[3]^dataatrec[5]^dataatrec[7];
    c2=dataatrec[2]^dataatrec[3]^dataatrec[6]^dataatrec[7];
    c3=dataatrec[4]^dataatrec[5]^dataatrec[6]^dataatrec[7];

    c = c3*4+c2*2+c1;

    if(c==0){
        cout<<"\n NO ERROR CONGO !!";
    }else{
        cout<<"\nerror on the position : "<<c;
        cout<<"\ncorrect one is :";
        if(dataatrec[c]==0){
            dataatrec[c]=1;
        }else{
            dataatrec[c]=0;
        }
        for(i=1;i<=7;i++){
            cout<<dataatrec[i];
        }
    }

    return 0;
}
