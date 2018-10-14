import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DetailPretComponent } from './detail-pret.component';
import { DetailPretDetailComponent } from './detail-pret-detail.component';
import { DetailPretPopupComponent } from './detail-pret-dialog.component';
import { DetailPretDeletePopupComponent } from './detail-pret-delete-dialog.component';

@Injectable()
export class DetailPretResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const detailPretRoute: Routes = [
    {
        path: 'detail-pret',
        component: DetailPretComponent,
        resolve: {
            'pagingParams': DetailPretResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.detailPret.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'detail-pret/:id',
        component: DetailPretDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.detailPret.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailPretPopupRoute: Routes = [
    {
        path: 'detail-pret-new',
        component: DetailPretPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.detailPret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-pret/:id/edit',
        component: DetailPretPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.detailPret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detail-pret/:id/delete',
        component: DetailPretDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.detailPret.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
