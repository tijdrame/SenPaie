import { BaseEntity } from './../../shared';
import {Collaborateur} from "../collaborateur";

export class Recap implements BaseEntity {
    constructor(
        public id?: number,

        public collaborateur?:Collaborateur,
        public brutFiscal?:number,
        public netAPayer?:number,
        public salaireBrutMensuel?:number,
        public impotSurRevenu?:number,
        public trimf?:number,
        public ipresPartSalariale?:number,
        public totRetenue?:number,
        public ipresPartPatronales?:number,

        public cssAccidentDeTravail?:number,

        public cssPrestationFamiliale?:number,

        public ipmPatronale?:number,

        public contributionForfaitaire?:number,

        public primeImposable?:number,

        public primeNonImposable?:number,

        public avantage?:number,
        public recapLigne?:number
    ) {
        this.collaborateur = new Collaborateur();
        this.brutFiscal=0;
        this.netAPayer =0;
        this.salaireBrutMensuel=0;
        this.impotSurRevenu=0;
        this.trimf=0;
        this.ipresPartSalariale=0;
        this.totRetenue=0;
        this.ipresPartPatronales=0;

        this.cssAccidentDeTravail=0;

        this.cssPrestationFamiliale=0;

        this.ipmPatronale=0;

        this.contributionForfaitaire=0;

        this.primeImposable=0;

        this.primeNonImposable=0;
        this.avantage = 0;
        this.recapLigne = 0;

    }
}
