import React, { Component } from 'react';
import { StyleSheet, SafeAreaView, Text, View , TouchableOpacity , ActivityIndicator } from 'react-native';
import api from '../Services/api'

export default class Main extends Component {
    static navigationOptions={
        tittle: "Chuck Norris App"
    };

    state = {
        fact: null,
    }
    componentDidMount(){
        this.getFact();
    };

    getFact = async () => {
        const response = await api.get('/jokes/random');
        const fact = response.data.value;
        
        this.setState({fact});
    };
 
    render() {

    return(
        <SafeAreaView style = {styles.container}>
            <View style = {styles.header}>
                <Text style = {styles.textHeader}> Chuck Norris Fun Facts</Text>
            </View>
            <View style ={styles.factbox}>
                
                <Text style = {styles.fact}>{this.state.fact}</Text>
                
            </View>

            <View style = {styles.containerButton}>
                <TouchableOpacity style = {styles.button} 
                onPress = {() => this.getFact()}>
                    <Text style = {styles.buttotext} >Get New Fact</Text>
                </TouchableOpacity>
            </View>
        </SafeAreaView>
        );
        }
    }

export const styles = StyleSheet.create({
    container:{
        backgroundColor:'#fafafa',
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    header:{
        position: 'absolute',
        top: 0,
        width: '100%',
        height: '30%',
    },
    textHeader: {
        color: '#FFF',
        fontSize: 29,
        textAlign: "center",
        alignSelf: "stretch",
        height: 50,
        backgroundColor: "#8A2BE2",
    },
    factbox:{
        padding: 30,
        backgroundColor: '#8A2BE2',
        borderWidth: 1,
        borderColor: '#DDD',
        borderRadius: 5,
        maxWidth: '95%',
        minWidth: '95%',
    },
    fact:{
        fontSize: 17,
        lineHeight: 24,
        color: '#fff',
    },
    containerButton :{
        width: '85%',
        position: 'absolute',
        bottom: 0,
    },
    button:{ 
        height: 42,
        borderRadius: 5,
        borderWidth: 2,
        borderColor: '#8A2BE2',
        backgroundColor: 'transparent',
        justifyContent: 'center', 
        alignItems: 'center',
        marginBottom: 30,
    },
    buttotext:{
        fontSize: 19,
        color: "#568",
    }
});
