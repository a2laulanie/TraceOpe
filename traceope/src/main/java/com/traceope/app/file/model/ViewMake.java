package com.traceope.app.file.model;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.traceope.app.R;

import java.util.ArrayList;

/**
 * Created by ale on 25/11/14.
 */
public class ViewMake {

    private View rootView;
    private BpeView excelView;

    public ViewMake(View rootView) {
        this.rootView = rootView;
    }

    public BpeView makeInputXmlFile() {

        excelView = new BpeView();

        //gestion des titres
        excelView.setTitreBpe((TextView) rootView.findViewById(R.id.TitleReaderStatus));
        excelView.setTitreBpeSaved((TextView) rootView.findViewById(R.id.titredetectiond2));


		/* Face A */

        excelView.setPipeColor_AA(this.makePipeColorList("A", "A", rootView));
        excelView.setPipeColor_AB(this.makePipeColorList("A", "B", rootView));

        excelView.setPipeNumber_AA(this.makePipeNumberList("A", "A", rootView));
        excelView.setPipeType_AA(this.makePipeTypeList("A", "A", rootView));

        excelView.setPipeRobe_AA(this.makePipeRobeList("A", "A", rootView));
        excelView.setPipeRobe_AB(this.makePipeRobeList("A", "B", rootView));

        excelView.setPipeOrigin_AA(this.makePipeOriginList("A", "A", rootView));
        excelView.setPipeOrigin_AB(this.makePipeOriginList("A", "B", rootView));

        excelView.setPipeColorName_AA(this.makePipeColorTextList("A", "A", rootView));
        excelView.setPipeColorName_AB(this.makePipeColorTextList("A", "B", rootView));

        excelView.setFibreType_AA(this.makeFibreTypeList("A", "A", rootView));
        excelView.setFibreType_AB(this.makeFibreTypeList("A", "B", rootView));
        excelView.setFibreNumber_AA(this.makeFibreNumberList("A", "A", rootView));
        excelView.setFibreNumber_AB(this.makeFibreNumberList("A", "B", rootView));

        excelView.setPipeStatus_AA(this.makePipeStatusListSpinner("A", "A", rootView));

		/* Face B */
        excelView.setPipeColor_BA(this.makePipeColorList("B", "A", rootView));
        excelView.setPipeColor_BB(this.makePipeColorList("B", "B", rootView));

        excelView.setPipeNumber_BA(this.makePipeNumberList("B", "A", rootView));
        excelView.setPipeType_BA(this.makePipeTypeList("B", "A", rootView));

        excelView.setPipeRobe_BA(this.makePipeRobeList("B", "A", rootView));
        excelView.setPipeRobe_BB(this.makePipeRobeList("B", "B", rootView));

        excelView.setPipeOrigin_BA(this.makePipeOriginList("B", "A", rootView));
        excelView.setPipeOrigin_BB(this.makePipeOriginList("B", "B", rootView));

        excelView.setPipeColorName_BA(this.makePipeColorTextList("B", "A", rootView));
        excelView.setPipeColorName_BB(this.makePipeColorTextList("B", "B", rootView));

        excelView.setFibreType_BA(this.makeFibreTypeList("B", "A", rootView));
        excelView.setFibreType_BB(this.makeFibreTypeList("B", "B", rootView));
        excelView.setFibreNumber_BA(this.makeFibreNumberList("B", "A", rootView));
        excelView.setFibreNumber_BB(this.makeFibreNumberList("B", "B", rootView));

        excelView.setPipeStatus_BA(this.makePipeStatusListText("B", "A", rootView));

        return excelView;


    }

    public ArrayList<TextView> makeFibreNumberList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_fibre_number_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA07));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA17));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA27));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA37));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA47));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA57));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA67));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA77));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA87));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA97));

        } else if (face == "A" && column == "B") {

            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA09));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA19));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA29));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA39));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA49));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA59));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA69));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA79));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA89));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewA99));

        } else if (face == "B" && column == "A") {

            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB07));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB17));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB27));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB37));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB47));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB57));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB67));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB77));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB87));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB97));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB107));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB117));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB127));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB137));

        } else if (face == "B" && column == "B") {

            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB09));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB19));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB29));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB39));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB49));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB59));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB69));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB79));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB89));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB99));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB109));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB119));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB129));
            pipe_fibre_number_list.add((TextView) rootView.findViewById(R.id.TextViewB139));

        } else {
            return null;
        }

        return pipe_fibre_number_list;

    }

    public ArrayList<TextView> makeFibreTypeList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_fibre_type_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA06));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA16));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA26));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA36));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA46));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA56));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA66));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA76));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA86));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA96));

        } else if (face == "A" && column == "B") {

            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA010));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA110));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA210));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA310));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA410));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA510));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA610));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA710));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA810));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewA910));

        } else if (face == "B" && column == "A") {

            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB06));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB16));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB26));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB36));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB46));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB56));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB66));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB76));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB86));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB96));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB106));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB116));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB126));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB136));

        } else if (face == "B" && column == "B") {

            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB010));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB110));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB210));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB310));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB410));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB510));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB610));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB710));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB810));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB910));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB1010));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB1110));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB1210));
            pipe_fibre_type_list.add((TextView) rootView.findViewById(R.id.TextViewB1310));

        } else {
            return null;
        }

        return pipe_fibre_type_list;

    }

    public ArrayList<TextView> makePipeColorTextList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_color_text_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA04));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA14));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA24));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA34));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA44));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA54));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA64));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA74));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA84));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA94));

        } else if (face == "A" && column == "B") {

            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA012));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA112));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA212));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA312));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA412));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA512));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA612));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA712));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA812));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewA912));

        } else if (face == "B" && column == "A") {

            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB04));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB14));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB24));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB34));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB44));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB54));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB64));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB74));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB84));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB94));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB104));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB11b4));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB124));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB134));

        } else if (face == "B" && column == "B") {

            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB012));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB112));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB212));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB312));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB412));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB512));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB612));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB712));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB812));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB912));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB1012));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB1112));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB1212));
            pipe_color_text_list.add((TextView) rootView.findViewById(R.id.TextViewB1312));

        } else {
            return null;
        }

        return pipe_color_text_list;

    }

    public ArrayList<TextView> makePipeOriginList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_origin_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA03));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA13));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA23));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA33));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA43));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA53));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA63));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA73));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA83));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA93));

        } else if (face == "A" && column == "B") {

            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA013));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA113));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA213));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA313));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA413));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA513));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA613));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA713));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA813));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewA913));

        } else if (face == "B" && column == "A") {

            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB03));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB13));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB23));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB33));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB43));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB53));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB63));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB73));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB83));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB93));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB103));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB11b3));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB123));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB133));

        } else if (face == "B" && column == "B") {

            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB013));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB113));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB213));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB313));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB413));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB513));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB613));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB713));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB813));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB913));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB1013));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB1113));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB1213));
            pipe_origin_list.add((TextView) rootView.findViewById(R.id.TextViewB1313));

        } else {
            return null;
        }

        return pipe_origin_list;

    }

    public ArrayList<TextView> makePipeRobeList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_robe_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA02));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA12));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA22));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA32));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA42));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA52));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA62));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA72));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA82));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA92));

        } else if (face == "A" && column == "B") {

            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA014));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA114));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA214));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA314));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA414));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA514));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA614));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA714));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA814));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewA914));

        } else if (face == "B" && column == "A") {

            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB02));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB12));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB22));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB32));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB42));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB52));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB62));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB72));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB82));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB92));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB102));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB11b2));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB122));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB132));

        } else if (face == "B" && column == "B") {

            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB014));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB114));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB214));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB314));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB414));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB514));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB614));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB714));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB814));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB914));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB1014));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB1114));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB1214));
            pipe_robe_list.add((TextView) rootView.findViewById(R.id.TextViewB1314));

        } else {
            return null;
        }

        return pipe_robe_list;

    }

    public ArrayList<TextView> makePipeNumberList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_number_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA00));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA10));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA20));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA30));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA40));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA50));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA60));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA70));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA80));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA90));

        } else if (face == "B" && column == "A") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB00));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB10));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB20));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB30));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB40));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB50));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB60));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB70));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB80));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB90));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB100));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB11b0));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB120));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB130));
        }

        return pipe_number_list;

    }

    public ArrayList<TextView> makePipeTypeList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_type_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA01));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA11));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA21));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA31));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA41));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA51));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA61));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA71));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA81));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewA91));

        } else if (face == "B" && column == "A") {
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB01));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB11));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB21));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB31));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB41));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB51));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB61));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB71));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB81));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB91));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB101));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB11b1));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB121));
            pipe_type_list.add((TextView) rootView.findViewById(R.id.TextViewB131));

        }

        return pipe_type_list;

    }

    public ArrayList<Spinner> makePipeStatusListText(String face, String column, View rootView) {

        ArrayList<Spinner> pipe_status_list = new ArrayList<Spinner>();

        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB08));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB18));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB28));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB38));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB48));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB58));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB68));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB78));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB88));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB98));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB108));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB118));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB128));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewB138));

        return pipe_status_list;

    }

    public ArrayList<Spinner> makePipeStatusListSpinner(String face, String column, View rootView) {

        ArrayList<Spinner> pipe_status_list = null;

        pipe_status_list = new ArrayList<Spinner>();

        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA08));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA18));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA28));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA38));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA48));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA58));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA68));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA78));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA88));
        pipe_status_list.add((Spinner) rootView.findViewById(R.id.TextViewA98));

        return pipe_status_list;

    }

    public ArrayList<TextView> makePipeColorList(String face, String column, View rootView) {

        ArrayList<TextView> pipe_number_list = new ArrayList<TextView>();

        if (face == "A" && column == "A") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA05));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA15));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA25));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA35));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA45));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA55));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA65));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA75));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA85));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA95));

        } else if (face == "A" && column == "B") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA011));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA111));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA211));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA311));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA411));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA511));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA611));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA711));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA811));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewA911));

        } else if (face == "B" && column == "A") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB05));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB15));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB25));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB35));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB45));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB55));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB65));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB75));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB85));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB95));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB105));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB115));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB125));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB135));

        } else if (face == "B" && column == "B") {

            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB011));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB111));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB211));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB311));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB411));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB511));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB611));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB711));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB811));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB911));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB1011));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB1111));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB1211));
            pipe_number_list.add((TextView) rootView.findViewById(R.id.TextViewB1311));

        } else {
            return null;
        }

        return pipe_number_list;

    }


}
