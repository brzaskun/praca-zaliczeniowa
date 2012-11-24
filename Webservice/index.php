<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        $client = new SoapClient("http://www.webservicex.net/genericbarcode.asmx?WSDL");
        $something = $client->__getFunctions();
        echo $something;
        foreach($something as $key => $value)
            {
                echo $key." has the value". $value;
                echo "<br/>";
            }
        echo $result = $client->__soapCall("GenerateBarCode", array(
        "height" => 20,
        "width" => 70,
        "angle" => 90,
        "ratio" => 10,
        "module" => 10,
        "left" => 10,
        "top" => 10,
        "checkSum" => true,
        "fontName" => "Arial",
        "barColor" => "#FFFFFF",
        "BGColor" => "#000000",
        "FontSize" => 10,
        "BarcodeOption" => "TYP",
        "BarcodeType" => "CODE_128_A",
        "CheckSumMethod" => "MODULO_10",
        "ShowTextPosition" => "TOP_LEFT",
        "BarCodeImageFormat" => "PNG"
        ),array (
            "barCodeText" => "lolo"));
        die();
        ?>
    </body>
</html>
