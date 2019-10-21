import React, {Component} from 'react';
import {
      View, Text,
      StyleSheet
} from 'react-native';

export default class Header extends Component{
    render(){
     return(
      <View style =  {styles.header}> 
        <Text style = {styles.headerTitle}>Chuck Jokes</Text> 
      </View>
     )  
    }
  };


const styles = StyleSheet.create({
  header:{
    backgroundColor: 'black',
    height: '11%',
    width: '100%',
    justifyContent: 'center', 
    
  },
  
  headerTitle: {
      fontSize: 20,
      fontWeight: '600',
      color: 'white',
      marginLeft: 20,
  },
});
  