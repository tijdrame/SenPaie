import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { StatutDemande } from './statut-demande.model';
import { StatutDemandeService } from './statut-demande.service';

@Injectable()
export class StatutDemandePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private statutDemandeService: StatutDemandeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.statutDemandeService.find(id)
                    .subscribe((statutDemandeResponse: HttpResponse<StatutDemande>) => {
                        const statutDemande: StatutDemande = statutDemandeResponse.body;
                        this.ngbModalRef = this.statutDemandeModalRef(component, statutDemande);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.statutDemandeModalRef(component, new StatutDemande());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    statutDemandeModalRef(component: Component, statutDemande: StatutDemande): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.statutDemande = statutDemande;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
