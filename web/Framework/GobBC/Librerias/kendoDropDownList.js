/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function refreshDrop(drop){
    drop.dataSource.query({
        page: 1
    });
    drop.dataSource.read();
}