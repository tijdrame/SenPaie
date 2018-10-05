import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DemandeConge } from './demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';

@Injectable()
export class DemandeCongePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private demandeCongeService: DemandeCongeService

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
                this.demandeCongeService.find(id)
                    .subscribe((demandeCongeResponse: HttpResponse<DemandeConge>) => {
                        const demandeConge: DemandeConge = demandeCongeResponse.body;
                        if (demandeConge.dateCreated) {
                            demandeConge.dateCreated = {
                                year: demandeConge.dateCreated.getFullYear(),
                                month: demandeConge.dateCreated.getMonth() + 1,
                                day: demandeConge.dateCreated.getDate()
                            };
                        }
                        if (demandeConge.dateDebut) {
                            demandeConge.dateDebut = {
                                year: demandeConge.dateDebut.getFullYear(),
                                month: demandeConge.dateDebut.getMonth() + 1,
                                day: demandeConge.dateDebut.getDate()
                            };
                        }
                        if (demandeConge.dateFin) {
                            demandeConge.dateFin = {
                                year: demandeConge.dateFin.getFullYear(),
                                month: demandeConge.dateFin.getMonth() + 1,
                                day: demandeConge.dateFin.getDate()
                            };
                        }
                        this.ngbModalRef = this.demandeCongeModalRef(component, demandeConge);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.demandeCongeModalRef(component, new DemandeConge());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    demandeCongeModalRef(component: Component, demandeConge: DemandeConge): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.demandeConge = demandeConge;
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
