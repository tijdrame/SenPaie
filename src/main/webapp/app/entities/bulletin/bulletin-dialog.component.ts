import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinPopupService } from './bulletin-popup.service';
import { BulletinService } from './bulletin.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { TypePaiement, TypePaiementService } from '../type-paiement';
import {BaseEntity, User, UserService} from '../../shared';
import { Remboursement, RemboursementService } from '../remboursement';
import {Structure, StructureService} from "../structure";
import {MembreFamille, MembreFamilleService} from "../membre-famille";
import {Bareme, BaremeService} from "../bareme";
import * as jsPDF from 'jspdf';
import {Fonction} from "../fonction";
import {TypeContrat} from "../type-contrat";
import {SituationMatrimoniale} from "../situation-matrimoniale";
import {Statut} from "../statut";
import {Nationalite} from "../nationalite";
import {Categorie} from "../categorie";
import {Convention} from "../convention";
import {Exercice, ExerciceService} from "../exercice";
import {PrimeCollab, PrimeCollabService} from "../prime-collab";
import {AvantageCollabService} from "../avantage-collab";
import {AvantageCollab} from "../avantage-collab/avantage-collab.model";
import {forEach} from "@angular/router/src/utils/collection";
import {Subject} from "rxjs/Subject";
import {MoisConcerne, MoisConcerneService} from "../mois-concerne";

@Component({
    selector: 'jhi-bulletin-dialog',
    templateUrl: './bulletin-dialog.component.html'
})
export class BulletinDialogComponent implements OnInit {

    bulletin: Bulletin;

    isSaving: boolean;

    collaborateurs: Collaborateur[];
    collaborateur: Collaborateur;
    fonction: Fonction;
    categorie: Categorie;
    nationalite: Nationalite;
    statut: Statut;
    situationMatrimoniale: SituationMatrimoniale;
    typeContrat: TypeContrat;
    convention: Convention;


    typepaiements: TypePaiement[];
    membreFamille: MembreFamille[];

    users: User[];
    structure: Structure;
    exercices: Exercice[];
    moisConcernes: MoisConcerne[];

    remboursements: Remboursement[];
    primesCollab: PrimeCollab[];
    avantagesCollab: AvantageCollab[];
    dateCreatedDp: any;
    dateUpdatedDp: any;
    dateDeletedDp: any;
    nbEnfant: number;
    nbFemme: number;
    nombreParts: number;
    bareme: Bareme;
    ipm: number = 0;
    totalRetenue: number = 0;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bulletinService: BulletinService,
        private collaborateurService: CollaborateurService,
        private typePaiementService: TypePaiementService,
        private userService: UserService,
        private remboursementService: RemboursementService,
        private eventManager: JhiEventManager,
        private structureService: StructureService,
        private baremeService: BaremeService,
        private exerciceService: ExerciceService,
        private moisService: MoisConcerneService,
        private primeCollabService: PrimeCollabService,
        private avantageCollabService: AvantageCollabService,
        private membreFamilleService: MembreFamilleService
    ) {
        this.getThestructure();
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
        this.typePaiementService.query()
            .subscribe((res: HttpResponse<TypePaiement[]>) => { this.typepaiements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.exerciceService.query()
            .subscribe((res: HttpResponse<Exercice[]>) => { this.exercices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.moisService.query()
            .subscribe((res: HttpResponse<MoisConcerne[]>) => { this.moisConcernes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bulletin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bulletinService.update(this.bulletin));
        } else {
            this.subscribeToSaveResponse(
                this.bulletinService.create(this.bulletin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bulletin>>) {
        result.subscribe((res: HttpResponse<Bulletin>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bulletin) {
        this.eventManager.broadcast({ name: 'bulletinListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCollaborateurById(index: number, item: Collaborateur) {
        return item.id;
    }

    trackTypePaiementById(index: number, item: TypePaiement) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackRemboursementById(index: number, item: Remboursement) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    getTotalRetenue(){
        this.getRetenues();
        if(this.bulletin.remboursements){
            for(let re of this.bulletin.remboursements)
                this.totalRetenue += re['montant'];
        }

        this.bulletin.totRetenue = this.totalRetenue;
        this.bulletin.netAPayer = this.bulletin.salaireBrutMensuel - this.totalRetenue;
    }

    getRetenues(){
        this.totalRetenue = 0;
        this.totalRetenue += this.bulletin.impotSurRevenu + this.bulletin.trimf + this.bulletin.ipresPartSalariale +
            this.ipm +this.bulletin.retenueIpm + this.bulletin.retenuePharmacie + this.bulletin.collaborateur['retenueRepas'];
        this.bulletin.totRetenue = this.totalRetenue;
        //console.log("sbrut "+this.bulletin.salaireBrutMensuel);
        //console.log("tot ret "+this.totalRetenue);
        this.bulletin.netAPayer = this.bulletin.salaireBrutMensuel - this.totalRetenue;
        //console.log("neta pay "+this.bulletin.netAPayer);
    }

    downLoadPdf(){
        let doc = new jsPDF();
        doc.fromHTML($('#myBulletin').get(0), 20, 20, {
            'width':500});
        doc.save('Test.pdf');
    }

    collabChoisi(collaborateur){
        console.log(collaborateur.primeTransport);
        this.collaborateur = collaborateur;
        this.nationalite = this.collaborateur.nationalite;
        this.categorie = this.collaborateur.categorie;
        this.fonction = this.collaborateur.fonction;
        this.statut = this.collaborateur.statut;
        this.situationMatrimoniale = this.collaborateur.situationMatrimoniale;
        this.typeContrat = this.collaborateur.typeContrat;

        this.ipm = this.structure.montantIpm;
        this.bulletin.ipmPatronale = this.structure.montantIpm;
        this.bulletin.brutFiscal = collaborateur.salaireDeBase + collaborateur.surSalaire;


        console.log(collaborateur.id);
        this.remboursementService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<Remboursement[]>) => {
                this.remboursements = res.body;
            });

        /*this.primeCollabService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<PrimeCollab[]>) => {
                this.primesCollab = res.body;
                this.bulletin.primeImposable = 0;
                this.bulletin.primeNonImposable = 0;
                this.primesCollab.forEach((it: PrimeCollab) =>{
                    console.log("prime=", it.montant+ " imposable?", it.prime['imposable']);
                    if(it.prime['imposable']){
                        this.bulletin.primeImposable += it.montant;
                        this.bulletin.brutFiscal += this.bulletin.primeImposable;
                    }

                    else this.bulletin.primeNonImposable += it.montant;
                });
            });*/
        /*this.avantageCollabService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<AvantageCollab[]>) => {
                this.avantagesCollab = res.body;
                this.bulletin.avantage = 0;
                this.avantagesCollab.forEach((it: AvantageCollab) =>{
                    console.log("avantag=", it.montant);
                    this.bulletin.avantage += it.montant;
                });
                this.bulletin.brutFiscal += this.bulletin.avantage ;
            });*/
        /*this.getAvantage().subscribe((resultat)=> {
            console.log("resultat sub===", resultat);
            this.bulletin.avantage = 0;
            this.bulletin.avantage = resultat;
            this.bulletin.brutFiscal += this.bulletin.avantage;
        });*/
        this.getPrimeImpo().subscribe((r)=>{
            this.bulletin.primeImposable = 0;
            this.bulletin.primeImposable = r;
            console.log("av===", this.bulletin.avantage);
            this.bulletin.brutFiscal += this.bulletin.primeImposable;
            this.getAvantage().subscribe((resultat)=>{
                console.log("resultat sub===",resultat);
                this.bulletin.avantage =0;
                this.bulletin.avantage = resultat;
                this.bulletin.brutFiscal += this.bulletin.avantage ;

                this.getPrimeNonImpo().subscribe((r)=>{
                    this.bulletin.primeNonImposable = 0;
                    this.bulletin.primeNonImposable = r;


                    this.bulletin.trimf = 1000;
                    this.bulletin.salaireBrutMensuel = this.bulletin.brutFiscal +collaborateur['primeTransport']+this.bulletin.primeNonImposable;
                    console.log("brut fiscal new===", this.bulletin.brutFiscal);
                    this.bulletin.cssAccidentDeTravail = 63000 * 0.03;
                    this.bulletin.cssPrestationFamiliale = 63000 * 0.07;
                    this.bulletin.contributionForfaitaire = Math.round(this.bulletin.brutFiscal * 0.03);

                    if(collaborateur['regime']['code']=="GEN"){
                        this.bulletin.ipresPartSalariale = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                            this.bulletin.brutFiscal * collaborateur.regime.tauxSalarial :
                            collaborateur.regime.plafond * collaborateur.regime.tauxSalarial;

                        this.bulletin.ipresPartPatronales = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                            this.bulletin.brutFiscal * collaborateur.regime.tauxPatronal :
                            collaborateur.regime.plafond * collaborateur.regime.tauxPatronal;
                    }else{
                        this.bulletin.ipresPartSalariale = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                            this.bulletin.brutFiscal * collaborateur.regime.tauxSalarial :
                            collaborateur.regime.plafond * collaborateur.regime.tauxSalarial;
                        this.bulletin.ipresPartPatronales = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                            this.bulletin.brutFiscal * collaborateur.regime.tauxPatronal :
                            collaborateur.regime.plafond * collaborateur.regime.tauxPatronal;
                    }
                    this.bulletin.ipresPartSalariale = Math.round(this.bulletin.ipresPartSalariale);


                    //console.log("brut", this.bulletin.brutFiscal);
                    let brutFisc = this.bulletin.brutFiscal / 1000;
                    brutFisc = Math.floor(brutFisc);
                    console.log("y",brutFisc);
                    brutFisc= brutFisc*1000;
                    console.log(brutFisc);


                    console.log("collaborateur"+collaborateur.prenom);
                    this.membreFamilleService.findByCollab(collaborateur.id)
                        .subscribe((res: HttpResponse<MembreFamille[]>) => { this.membreFamille = res.body;
                                this.nbEnfant = 0;
                                this.nbFemme = 0;
                                this.nombreParts = 1;
                                let aFemme : boolean = false;
                                let isActif : boolean = false;
                                //let nbP : number = 0;
                                for(let it of this.membreFamille){
                                    //nbP = it.typeRelation
                                    if(it.typeRelation['code']=="ENFANT"){
                                        this.nbEnfant += 1;
                                        this.nombreParts += it.typeRelation['nbParts'];
                                    }
                                    else{
                                        this.nbFemme += 1;
                                        aFemme = true;
                                        if(it.isActif)isActif = true;
                                    }
                                }
                                if(aFemme) this.nombreParts += 0.5;
                                if(!isActif && aFemme) this.nombreParts += 0.5;
                                this.bulletin.nbFemmes = this.nbFemme;
                                this.bulletin.nbEnfants = this.nbEnfant;
                                this.bulletin.nbPart = this.nombreParts;
                                this.baremeService.findByRevenue(brutFisc)
                                    .subscribe((baremeResponse: HttpResponse<Bareme>) => {
                                        this.bareme = baremeResponse.body;
                                        console.log(this.nombreParts==3);
                                        console.log(this.nombreParts===3);
                                        console.log(this.nombreParts);
                                        console.log(this.nombreParts===1.5);
                                        if(this.nombreParts==1) this.bulletin.impotSurRevenu = this.bareme.unePart;
                                        else if(this.nombreParts==1.5) this.bulletin.impotSurRevenu = this.bareme.unePartEtDemi;
                                        else if(this.nombreParts===2) this.bulletin.impotSurRevenu = this.bareme.deuxParts;
                                        else if(this.nombreParts==2.5) this.bulletin.impotSurRevenu = this.bareme.deuxPartsEtDemi;
                                        else if(this.nombreParts==3) this.bulletin.impotSurRevenu = this.bareme.troisParts;
                                        else if(this.nombreParts==3.5) this.bulletin.impotSurRevenu = this.bareme.troisPartsEtDemi;
                                        else if(this.nombreParts==4) this.bulletin.impotSurRevenu = this.bareme.quatreParts;
                                        else if(this.nombreParts==4.5) this.bulletin.impotSurRevenu = this.bareme.quatrePartsEtDemi;
                                        else this.bulletin.impotSurRevenu = this.bareme.cinqParts;
                                        this.bulletin.impotSurRevenu = Math.round(this.bulletin.impotSurRevenu);
                                        this.getRetenues();
                                    });
                            },
                            (res: HttpErrorResponse) => this.onError(res.message));
                });


            });
        });




        /*this.bulletin.trimf = 1000;
        this.bulletin.salaireBrutMensuel = this.bulletin.brutFiscal +collaborateur['primeTransport'];
        console.log("brut fiscal new===", this.bulletin.brutFiscal);
        this.bulletin.cssAccidentDeTravail = 63000 * 0.03;
        this.bulletin.cssPrestationFamiliale = 63000 * 0.07;
        this.bulletin.contributionForfaitaire = Math.round(this.bulletin.brutFiscal * 0.03);

        if(collaborateur['regime']['code']=="GEN"){
            this.bulletin.ipresPartSalariale = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                this.bulletin.brutFiscal * collaborateur.regime.tauxSalarial :
                collaborateur.regime.plafond * collaborateur.regime.tauxSalarial;

            this.bulletin.ipresPartPatronales = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                this.bulletin.brutFiscal * collaborateur.regime.tauxPatronal :
                collaborateur.regime.plafond * collaborateur.regime.tauxPatronal;
        }else{
            this.bulletin.ipresPartSalariale = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                this.bulletin.brutFiscal * collaborateur.regime.tauxSalarial :
                collaborateur.regime.plafond * collaborateur.regime.tauxSalarial;
            this.bulletin.ipresPartPatronales = this.bulletin.brutFiscal <= collaborateur.regime.plafond ?
                this.bulletin.brutFiscal * collaborateur.regime.tauxPatronal :
                collaborateur.regime.plafond * collaborateur.regime.tauxPatronal;
        }
        this.bulletin.ipresPartSalariale = Math.round(this.bulletin.ipresPartSalariale);

        let x=5.25;
        x = Math.round(x)
        console.log("xxxxxx",x);
        console.log("brut", this.bulletin.brutFiscal);
        let brutFisc = this.bulletin.brutFiscal / 1000;
        brutFisc = Math.floor(brutFisc);
        console.log("y",brutFisc);
        brutFisc= brutFisc*1000;
        console.log(brutFisc);


        console.log("collaborateur"+collaborateur.prenom);
        this.membreFamilleService.findByCollab(collaborateur.id)
            .subscribe((res: HttpResponse<MembreFamille[]>) => { this.membreFamille = res.body;
            this.nbEnfant = 0;
            this.nbFemme = 0;
            this.nombreParts = 1;
            let aFemme : boolean = false;
            let isActif : boolean = false;
            //let nbP : number = 0;
            for(let it of this.membreFamille){
                //nbP = it.typeRelation
                    if(it.typeRelation['code']=="ENFANT"){
                        this.nbEnfant += 1;
                        this.nombreParts += it.typeRelation['nbParts'];
                    }
                    else{
                        this.nbFemme += 1;
                        aFemme = true;
                        if(it.isActif)isActif = true;
                    }
                }
                if(aFemme) this.nombreParts += 0.5;
            if(!isActif && aFemme) this.nombreParts += 0.5;
                    this.bulletin.nbFemmes = this.nbFemme;
                    this.bulletin.nbEnfants = this.nbEnfant;
                    this.bulletin.nbPart = this.nombreParts;
                    this.baremeService.findByRevenue(brutFisc)
                        .subscribe((baremeResponse: HttpResponse<Bareme>) => {
                            this.bareme = baremeResponse.body;
                            console.log(this.nombreParts==3);
                            console.log(this.nombreParts===3);
                            console.log(this.nombreParts);
                            console.log(this.nombreParts===1.5);
                            if(this.nombreParts==1) this.bulletin.impotSurRevenu = this.bareme.unePart;
                            else if(this.nombreParts==1.5) this.bulletin.impotSurRevenu = this.bareme.unePartEtDemi;
                            else if(this.nombreParts===2) this.bulletin.impotSurRevenu = this.bareme.deuxParts;
                            else if(this.nombreParts==2.5) this.bulletin.impotSurRevenu = this.bareme.deuxPartsEtDemi;
                            else if(this.nombreParts==3) this.bulletin.impotSurRevenu = this.bareme.troisParts;
                            else if(this.nombreParts==3.5) this.bulletin.impotSurRevenu = this.bareme.troisPartsEtDemi;
                            else if(this.nombreParts==4) this.bulletin.impotSurRevenu = this.bareme.quatreParts;
                            else if(this.nombreParts==4.5) this.bulletin.impotSurRevenu = this.bareme.quatrePartsEtDemi;
                            else this.bulletin.impotSurRevenu = this.bareme.cinqParts;
                            this.bulletin.impotSurRevenu = Math.round(this.bulletin.impotSurRevenu);
                            this.getRetenues();
                        });
                },
                (res: HttpErrorResponse) => this.onError(res.message));*/


    }

    getThestructure(){
        this.structureService.findDenom("Novatech - SA")
            .subscribe((structureResponse: HttpResponse<Structure>) => {
                this.structure = structureResponse.body;
                this.convention = this.structure.convention;
            });
    }

    getAvantage(){
        let subject : Subject<number> = new Subject();
        let total: number =0;
        this.avantageCollabService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<AvantageCollab[]>) => {
                this.avantagesCollab = res.body;
                this.bulletin.avantage = 0;
                this.avantagesCollab.forEach((it: AvantageCollab) =>{
                    console.log("avantag=", it.montant);
                    total += it.montant;
                    subject.next(total);
                });
                if(total==0)
                subject.next(total);
            });
        return subject.asObservable();
    }

    getPrimeImpo(){
        let subject : Subject<number> = new Subject();
        let total: number =0;
        this.primeCollabService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<PrimeCollab[]>) => {
                this.primesCollab = res.body;
                this.primesCollab.forEach((it: PrimeCollab) =>{
                    console.log("prime=", it.montant+ " imposable?", it.prime['imposable']);
                    if(it.prime['imposable']){
                        total += it.montant;
                        subject.next(total);
                    }
                });
                if(total==0)
                subject.next(total);
            });
        return subject.asObservable();
    }

    getPrimeNonImpo(){
        let subject : Subject<number> = new Subject();
        let total: number =0;
        this.primeCollabService.findByCollab(this.bulletin.collaborateur.id)
            .subscribe((res: HttpResponse<PrimeCollab[]>) => {
                this.primesCollab = res.body;
                this.primesCollab.forEach((it: PrimeCollab) =>{
                    console.log("prime=", it.montant+ " imposable?", it.prime['imposable']);
                    if(!it.prime['imposable']){
                        total += it.montant;
                        subject.next(total);
                    }
                });
                if(total==0)
                subject.next(total);
            });
        return subject.asObservable();
    }

}



@Component({
    selector: 'jhi-bulletin-popup',
    template: ''
})
export class BulletinPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bulletinPopupService: BulletinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bulletinPopupService
                    .open(BulletinDialogComponent as Component, params['id']);
            } else {
                this.bulletinPopupService
                    .open(BulletinDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
