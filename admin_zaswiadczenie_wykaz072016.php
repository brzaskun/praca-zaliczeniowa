<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
    <div id="tbl" style="max-width: 1200px;">
    </div>
    <div id='editzaswiadczeniewykaz'  style='margin-top: 10px; display: none;'  title="Edycja zaświadczenia">
        <form id="formeditzaswiadczeniewykaz">
            <table  id="tabelaeditzaswiadczeniewykaz" style="margin-bottom: 15px;">
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="id" name="id" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>unikalna nazwa: </span></td><td><input id="nazwazaswiadczenia" name="nazwazaswiadczenia" style="width: 350px;" disabled></td> 
                </tr>
                <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="skrot" name="skrot" style="width: 350px;"></td>
                </tr> 
                <tr>
                    <td><span>poziom: </span></td><td><input id="poziom" name="poziom" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 1: </span></td><td><input id="linia1" name="linia1" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 2: </span></td><td><input id="linia2" name="linia2" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 3: </span></td><td><input id="linia3" name="linia3" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>plik pdf: </span></td><td><input id="pdf" name="pdf" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>treść M: </span></td><td><textarea id="trescM" name="trescM" type="text"
                                                            cols="55" 
                                                            rows="6" 
                                                            ></textarea></td> 
                </tr>
                <tr>
                    <td><span>treść K: </span></td><td><textarea id="trescK" name="trescK" type="text"
                                                            cols="55" 
                                                            rows="6" 
                                                            ></textarea></td> 
                </tr>
            </table>
            <input value="edytuj" type="button" onclick="edytujzaswiadczeniephp();"  style="padding: 5px; width: 120px; margin-left: 20%;">
            <input value="rezygnuj" type="button" onclick="canceledytujzaswiadczenie()"  style="padding: 5px; width: 120px; margin-left: 5%;">
           
<!--            <input value="edytuj" type="button" onclick="edytujtabeleszkoleniewykaz();"  style="padding: 5px; width: 120px; margin-left: 35%;">-->
        </form>
    </div>
    <div id='nowezaswiadczeniewykaz'  style='margin-top: 10px; display: none;'  title="Nowe zaświadczenie">
        <form id="formnowezaswiadczeniewykaz">
            <table   style="margin-bottom: 15px;">
                <tr>
                    <td><span>unikalna nazwa: </span></td><td><input id="Nnazwazaswiadczenia" name="Nnazwazaswiadczenia" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="Nskrot" name="Nskrot" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>poziom: </span></td><td><input id="Npoziom" name="Npoziom" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 1: </span></td><td><input id="Nlinia1" name="Nlinia1" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 2: </span></td><td><input id="Nlinia2" name="Nlinia2" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>linia 3: </span></td><td><input id="Nlinia3" name="Nlinia3" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>plik pdf: </span></td><td><input id="Npdf" name="Npdf" style="width: 350px;"></td>
                </tr>
               <tr>
                    <td><span>treść M: </span></td><td><textarea id="NtrescM" name="NtrescM" type="text"
                                                            cols="55" 
                                                            rows="6" 
                                                            ></textarea></td>
                </tr>
                <tr>
                    <td><span>treść K: </span></td><td><textarea id="NtrescK" name="NtrescK" type="text"
                                                            cols="55" 
                                                            rows="6" 
                                                            ></textarea></td> 
                </tr>
            </table>
            <input value="dodaj" type="button" onclick="dodajnowezaswiadczeniewykaz();"  style="padding: 5px; width: 120px; margin-left: 20%;">
            <input value="rezygnuj" type="button" onclick="$('#nowezaswiadczeniewykaz').puidialog('hide');"  style="padding: 5px; width: 120px; margin-left: 5%;">
        </form>
    </div>
    <div id="dialog_usun" title="" style="display: none;">
        <p>Czy napewno usunąć?</p> 
        <input value="tak" style="float: left; margin-left: 4%; width: 120px;" type="button"  
               onclick="tak_usunwiersz();"/> 
        <input value="nie" type="button" onclick="nie_usunwiersz();" style="float: right; margin-right: 4%; width: 120px;"/>
</div>
</div>