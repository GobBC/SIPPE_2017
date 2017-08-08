
   
    var formatoFecha = "dd/mm/yyyy"
    var diasInhabiles = [
          [1, 1], [3, 5], [3, 21],
          [5, 1],[5, 5], [9, 16], 
          [11, 2], [11, 20], [12, 5],
          [12,25]
        ];

    function DiasFestivos(date) {
        for (i = 0; i < diasInhabiles.length; i++) {
          if (date.getMonth() == diasInhabiles[i][0] - 1
              && date.getDate() == diasInhabiles[i][1]) {
            return [false, diasInhabiles[i][2] + '_day'];
          }
        }
      return [true, ''];
    }
  
     function DiasNoLaborales(date) {
            var noWeekend = $.datepicker.noWeekends(date);
            if (noWeekend[0]) {
                return DiasFestivos(date);
            } else {
                return noDomingo(date);
        }
     }    
             
     function noDomingo(date){
      var day = date.getDay();
           if (day > 0)
               return DiasFestivos(date)
           else
           return [false, ''];
                  
     };   


function calendarioDiasInhabiles(campo,formatoFecha,diasMinimos,imagen){   
     $(function()  {
         
            
            $.datepicker.setDefaults({beforeShowDay: DiasNoLaborales ,
                    dateFormate:formatoFecha, minDate: diasMinimos, 
                    showOn: 'button', buttonImage: imagen, buttonImageOnly: true,
                    buttonText: 'Calendario'})
        
            $("#"+campo+"").datepicker({nextText: 'Siguiente',prevText: 'Anterior'})        
                
      });
    
}

function calendarioSinDiasInhabiles(campo,formatoFecha,imagen){
     $(function()  {
         
            
            $.datepicker.setDefaults({dateFormate:formatoFecha, 
                    showOn: 'button', buttonImage: imagen, buttonImageOnly: true,
                    buttonText: 'Calendario'})
   
            $("#"+campo+"").datepicker({nextText: 'Siguiente',prevText: 'Anterior'})        
                
      });
    
}