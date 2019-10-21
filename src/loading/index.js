import React, {Component} from 'react';
import {
      View, Text, StyleSheet,
      ActivityIndicator
} from 'react-native';

export default class Loading extends Component{
    render(){
     return(
        <View style={styles.start}>
            <Text style = {styles.text}>Carregando</Text>         
            <ActivityIndicator size="large" color="#0000ff" />
        </View>
     )
  
    }
  };

  const styles = StyleSheet.create({
    start: {
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 300
    },
    
    text:{
        fontSize:20,
        padding: 20,
        color: 'black'
    }


  });
