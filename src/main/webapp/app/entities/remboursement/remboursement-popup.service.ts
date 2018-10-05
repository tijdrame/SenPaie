import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Remboursement } from './remboursement.model';
import { RemboursementService } from './remboursement.service';

@Injectable()
export class RemboursementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private remboursementService: RemboursementService

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
                this.remboursementService.find(id)
                    .subscribe((remboursementResponse: HttpResponse<Remboursement>) => {
                        const remboursement: Remboursement = remboursementResponse.body;
                        if (remboursement.dateRemboursement) {
                            remboursement.dateRemboursement = {
                                year: remboursement.dateRemboursement.getFullYear(),
                                month: remboursement.dateRemboursement.getMonth() + 1,
                                day: remboursement.dateRemboursement.getDate()
                            };
                        }
                        this.ngbModalRef = this.remboursementModalRef(component, remboursement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.remboursementModalRef(component, new Remboursement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    remboursementModalRef(component: Component, remboursement: Remboursement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.remboursement = remboursement;
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
