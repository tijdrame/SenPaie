import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MembreFamille } from './membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';

@Injectable()
export class MembreFamillePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private membreFamilleService: MembreFamilleService

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
                this.membreFamilleService.find(id)
                    .subscribe((membreFamilleResponse: HttpResponse<MembreFamille>) => {
                        const membreFamille: MembreFamille = membreFamilleResponse.body;
                        if (membreFamille.dateNaissance) {
                            membreFamille.dateNaissance = {
                                year: membreFamille.dateNaissance.getFullYear(),
                                month: membreFamille.dateNaissance.getMonth() + 1,
                                day: membreFamille.dateNaissance.getDate()
                            };
                        }
                        if (membreFamille.dateMariage) {
                            membreFamille.dateMariage = {
                                year: membreFamille.dateMariage.getFullYear(),
                                month: membreFamille.dateMariage.getMonth() + 1,
                                day: membreFamille.dateMariage.getDate()
                            };
                        }
                        this.ngbModalRef = this.membreFamilleModalRef(component, membreFamille);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.membreFamilleModalRef(component, new MembreFamille());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    membreFamilleModalRef(component: Component, membreFamille: MembreFamille): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.membreFamille = membreFamille;
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
