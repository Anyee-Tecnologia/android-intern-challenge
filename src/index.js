import React, {Component} from 'react';
import {
      View,
      StyleSheet, Text, Image,
      TouchableOpacity, Dimensions
} from 'react-native';

import Header from './header';
import Loading from './loading';

export default class App extends Component{
  constructor(props){
    super(props);
    this.state =  {isLoading: true, biggest: false}
    

  };  
  componentDidMount(){
    this.getJoke(); 
  }

  getJoke = () => {
    fetch('https://api.chucknorris.io/jokes/random')
    .then((response) =>  response.json())
    .then((responseJson) => {
      this.setState({
      isLoading: false,
      icon_url: responseJson.icon_url,
      id: responseJson.id,
      url: responseJson.url,
      value: responseJson.value,
           
    }, function(){});
    
  })
    .catch((error) => {
      console.log(error)
    });

  };
 
  change = () =>{
    this.getJoke();
    
   
  }

 
  render(){
    if(this.state.isLoading){
      return(
        <Loading/>
      )
    }else{
    
      const image = this.state.icon_url.toString();  
     
      return (
         <>  
        <View style = {styles.container}>
          <Header/>          
            <View style={(this.state.value.toString().length >250)?styles.sectionBiggest : styles.section}>
            <Text style = {(this.state.value.toString().length >250) ? styles.sectionDescriptionBiggest:styles.sectionDescription}> {this.state.value}</Text> 
            
          </View> 
        
          <View style = {styles.author}>  
            <Image style={styles.images}
              source = {{uri: image}} 
            />
            <Text style = {styles.txt}>Chuck Norris</Text>          
            
          </View>  
        </View>

        

        <View style = {{backgroundColor:'#f8f8ff'}}>         
            <TouchableOpacity  style = {styles.button} 
              onPress = {this.change}> 
              <Text style = {{color:'white'}}>TELL ME ANOTHER JOKE !</Text> 
            </TouchableOpacity>
        </View>  

        </>

        )
        
      }    
    }

  };

const styles = StyleSheet.create({
  container:{
    flex: 1,
    flexDirection: 'column',
    backgroundColor: '#f8f8ff',
    
  },
  
  section: {
    height: '40%',
    marginLeft: 40,
    marginRight: 40,
    backgroundColor : '#e6e6e6',
    justifyContent: 'center',
    marginTop: 60,
   
    borderRadius: 10,
    borderBottomLeftRadius: 0,
    borderBottomRightRadius: 0,
    
  },
   
  sectionDescription: {
    fontSize: 19,
    color: '#303136',   
    padding: 10,
    paddingBottom: 15,    
    textAlign: 'justify',
    alignSelf: 'center',    
    
  },

  sectionBiggest: {
    height: '60%',
    marginTop: '10%',   
    marginLeft: 40,
    marginRight: 40,
    
    justifyContent: 'center',
    backgroundColor : '#e6e6e6',
    borderRadius: 10,
    borderBottomLeftRadius: 0,
    borderBottomRightRadius: 0,
    
  },
   
  sectionDescriptionBiggest: {
    fontSize: 18,
    color: 'black',   
    padding: 10,
    textAlign: 'justify',
    alignSelf: 'center',
  },

  author:{
    height:55,    
    backgroundColor: 'orange',
    flexDirection:'row' ,
    alignItems: 'center',
    
    marginLeft: 40,
    marginRight: 40,    
    borderRadius: 10,
    borderColor:'black',
    borderTopLeftRadius: 0,
    borderTopRightRadius: 0,
    borderTopWidth: 0.9 ,
    
  },  

  txt:{
    marginTop: 10,
    fontSize: 25,
    justifyContent: 'center',
    alignItems:"center",
    marginLeft: 10,
  },

  
  images: {
    width: 50, 
    height: 50,
    justifyContent: 'center'
  },

  button: {
    height: 60,
    marginLeft: 20,
    marginRight: 20,
    marginBottom: 30,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'black',
    borderRadius: 10,
    borderColor: 'black',
    
    
  },

}); 

