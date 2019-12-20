/**
 *  dim-a-device.app.groovy
 *  Dim A Device
 *
 *  Author: todd@wackford.net
 *  Date: 2013-11-12
 */
/**
 *  App Name:   Dim A Sevice
 *
 *  Author: 	Barbara Schiavinato
 *
 *  Date: 	2019-12-20
 *  Version: 	0.1
 *  
 */


// Automatically generated. Make future change here.
definition(
    name: "Dim A Sevice",
    namespace: "barabba9174",
    description: "Follows the dimmer level of another dimmer",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"
)

preferences {
	section("When this...") { 
		input "masters", "capability.switchLevel", 
			multiple: false, 
			title: "Master Dimmer Switch...", 
			required: true
	}

	section("Then these will follow with on/off...") {
		input "slaves2", "capability.switch", 
			multiple: true, 
			title: "Slave On/Off Switch(es)...", 
			required: false
	}
    
	section("And these will follow with dimming level...") {
		input "slaves", "capability.switchLevel", 
			multiple: true, 
			title: "Slave Dimmer Switch(es)...", 
			required: true
	}
}

def installed()
{
    subscribe(masters, "switch.on", switchOnHandler)
    subscribe(masters, "switch.off", switchOffHandler)
    subscribe(masters, "level", switchSetLevelHandler)
    log.info "installed to all of switches events"
}

def updated()
{
	unsubscribe()
    subscribe(masters, "switch.on", switchOnHandler)
    subscribe(masters, "switch.off", switchOffHandler)
    subscribe(masters, "level", switchSetLevelHandler)
    log.info "subscribed to all of switches events"
}

def switchSetLevelHandler(evt)
{	

	log.info "switchSetLevelHandler event: ${evt}"
    log.info "switchSetLevelHandler value: ${evt.value}"
    def latestValue = masters.latestValue("level");
    
    log.info "switchSetLevelHandler Event: ${latestValue}"
	slaves?.setLevel(latestValue)
}

def switchOffHandler(evt) {
	log.info "switchoffHandler Event: ${evt.value}"
	slaves?.off()
	slaves2?.off()
}

def switchOnHandler(evt) {
    log.info "switchOnHandler Event: ${evt.value}"
    slaves?.setLevel(masters.latestValue("level"))
    //slaves?.on()
    slaves2?.on()
}