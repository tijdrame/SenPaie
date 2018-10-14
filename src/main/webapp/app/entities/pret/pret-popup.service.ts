import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Pret } from './pret.model';
import { PretService } from './pret.service';

@Injectable()
export class PretPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pretService: PretService

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
                this.pretService.find(id)
                    .subscribe((pretResponse: HttpResponse<Pret>) => {
                        const pret: Pret = pretResponse.body;
                        if (pret.datePret) {
                            pret.datePret = {
                                year: pret.datePret.getFullYear(),
                                month: pret.datePret.getMonth() + 1,
                                day: pret.datePret.getDate()
                            };
                        }
                        if (pret.dateDebutRemboursement) {
                            pret.dateDebutRemboursement = {
                                year: pret.dateDebutRemboursement.getFullYear(),
                                month: pret.dateDebutRemboursement.getMonth() + 1,
                                day: pret.dateDebutRemboursement.getDate()
                            };
                        }
                        this.ngbModalRef = this.pretModalRef(component, pret);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pretModalRef(component, new Pret());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pretModalRef(component: Component, pret: Pret): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pret = pret;
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
